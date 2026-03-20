"""
Minigram - Database Seed Script
================================
Creates 20 users, logs them in, creates 10 posts per user (parallel),
then makes each user follow a random subset of others.

Usage:
    python seed.py

Requirements:
    pip install requests faker

Folder structure expected:
    seed.py
    img/
        1.png
        2.png
        ...
        10.png
"""

import requests
import random
import json
import concurrent.futures
import threading
from datetime import date, timedelta

# ──────────────────────────────────────────────
# CONFIGURATION
# ──────────────────────────────────────────────
BASE_URL = "http://localhost:8080/api/v1"
PASSWORD = "mng123456"          # Must satisfy min-8-chars constraint
MAX_WORKERS = 5                  # Parallel threads for user creation + posts
IMG_FOLDER = "./img"
IMG_COUNT = 10                   # img/1.png ... img/10.png

# ──────────────────────────────────────────────
# USER DATA  (20 users)
# ──────────────────────────────────────────────
USERS = [
    {"username": "alex_vm",      "fullname": "Alejandro Vargas",    "gender": "M"},
    {"username": "sofia_rp",     "fullname": "Sofia Reyes",         "gender": "F"},
    {"username": "carlos_mn",    "fullname": "Carlos Mendoza",      "gender": "M"},
    {"username": "lucia_fb",     "fullname": "Lucia Fernández",     "gender": "F"},
    {"username": "diego_oc",     "fullname": "Diego Ochoa",         "gender": "M"},
    {"username": "valeria_sg",   "fullname": "Valeria Salgado",     "gender": "F"},
    {"username": "miguel_hr",    "fullname": "Miguel Herrera",      "gender": "M"},
    {"username": "ana_tb",       "fullname": "Ana Torres",          "gender": "F"},
    {"username": "javier_pc",    "fullname": "Javier Paredes",      "gender": "M"},
    {"username": "isabella_lm",  "fullname": "Isabella Lara",       "gender": "F"},
    {"username": "mateo_cv",     "fullname": "Mateo Castro",        "gender": "M"},
    {"username": "camila_nz",    "fullname": "Camila Núñez",        "gender": "F"},
    {"username": "sebastian_aq", "fullname": "Sebastian Aguirre",   "gender": "M"},
    {"username": "mariana_de",   "fullname": "Mariana Diaz",        "gender": "F"},
    {"username": "andres_vl",    "fullname": "Andres Villanueva",   "gender": "M"},
    {"username": "paula_rj",     "fullname": "Paula Rojas",         "gender": "F"},
    {"username": "nicolas_bm",   "fullname": "Nicolas Bermudez",    "gender": "M"},
    {"username": "daniela_qr",   "fullname": "Daniela Quiroga",     "gender": "F"},
    {"username": "felipe_sc",    "fullname": "Felipe Salcedo",      "gender": "M"},
    {"username": "renata_gp",    "fullname": "Renata Gutierrez",    "gender": "F"},
]

POST_DESCRIPTIONS = [
    "Disfrutando el momento",
    "Dias como este son los mejores",
    "Explorando nuevos lugaress️",
    "La vida es corta, disfrutala",
    "Capturando recuerdos",
    "Un dia increible con gente increible",
    "Nada como la buena compañia",
    "El mejor plan de la semana",
    "Momentos que no se olvidan",
    "Siguiendo el camino",
    "Cada dia es una nueva aventura",
    "Sin filtros, solo vibes",
    "Esto si que es vivir",
    "Nuevo dia, nueva energia",
    "Feliz donde estoy",
]

# ──────────────────────────────────────────────
# HELPERS
# ──────────────────────────────────────────────
print_lock = threading.Lock()

def log(msg: str):
    with print_lock:
        print(msg)

def random_birthdate() -> str:
    """Returns a birthdate string for someone between 18 and 40 years old."""
    today = date.today()
    days_offset = random.randint(18 * 365, 40 * 365)
    bd = today - timedelta(days=days_offset)
    return bd.isoformat()

def random_image_path() -> str:
    n = random.randint(1, IMG_COUNT)
    return f"{IMG_FOLDER}/{n}.jpg"

# ──────────────────────────────────────────────
# STEP 1 — Sign up
# ──────────────────────────────────────────────
def signup(user: dict) -> bool:
    url = f"{BASE_URL}/auth/signUp"
    payload = {
        "email":     f"{user['username']}@minigram.dev",
        "username":  user["username"],
        "password":  PASSWORD,
        "fullname":  user["fullname"],
        "birthdate": random_birthdate(),
        "gender":    user["gender"],
    }
    try:
        resp = requests.post(url, json=payload, timeout=10)
        if resp.status_code in (200, 201):
            log(f"  [SIGNUP ✓] {user['username']}")
            return True
        else:
            log(f"  [SIGNUP ✗] {user['username']} → {resp.status_code}: {resp.text[:120]}")
            return False
    except Exception as e:
        log(f"  [SIGNUP ✗] {user['username']} → {e}")
        return False

# ──────────────────────────────────────────────
# STEP 2 — Login → get access token
# ──────────────────────────────────────────────
def login(user: dict) -> str | None:
    url = f"{BASE_URL}/auth/login"
    payload = {
        "username": user["username"],
        "password": PASSWORD,
    }
    try:
        resp = requests.post(url, json=payload, timeout=10)
        if resp.status_code == 200:
            token = resp.json().get("accessToken")
            log(f"  [LOGIN  ✓] {user['username']}")
            return token
        else:
            log(f"  [LOGIN  ✗] {user['username']} → {resp.status_code}: {resp.text[:120]}")
            return None
    except Exception as e:
        log(f"  [LOGIN  ✗] {user['username']} → {e}")
        return None

# ──────────────────────────────────────────────
# STEP 3 — Create a single post
# ──────────────────────────────────────────────
def create_post(username: str, token: str, post_index: int) -> bool:
    url = f"{BASE_URL}/post"
    headers = {"Authorization": f"Bearer {token}"}
    description = random.choice(POST_DESCRIPTIONS)
    img_path = random_image_path()

    data_payload = json.dumps({"description": description})

    try:
        with open(img_path, "rb") as img_file:
            files = {
                "data":  (None, data_payload, "application/json"),
                "image": (f"post_{post_index}.png", img_file, "image/jpeg"),
            }
            resp = requests.post(url, headers=headers, files=files, timeout=20)

        if resp.status_code in (200, 201):
            log(f"    [POST ✓] {username} → post #{post_index}")
            return True
        else:
            log(f"    [POST ✗] {username} → post #{post_index} → {resp.status_code}: {resp.text[:120]}")
            return False
    except Exception as e:
        log(f"    [POST ✗] {username} → post #{post_index} → {e}")
        return False

# ──────────────────────────────────────────────
# STEP 4 — Follow users
# ──────────────────────────────────────────────
def follow_users(follower: dict, token: str, all_usernames: list[str]):
    """Each user follows a random subset (between 3 and 15) of the other users."""
    others = [u for u in all_usernames if u != follower["username"]]
    targets = random.sample(others, k=random.randint(3, min(15, len(others))))
    headers = {"Authorization": f"Bearer {token}"}

    for target in targets:
        url = f"{BASE_URL}/users/{target}/follows"
        try:
            resp = requests.post(url, headers=headers, timeout=10)
            if resp.status_code == 204:
                log(f"  [FOLLOW ✓] {follower['username']} → {target}")
            else:
                log(f"  [FOLLOW ✗] {follower['username']} → {target} → {resp.status_code}: {resp.text[:80]}")
        except Exception as e:
            log(f"  [FOLLOW ✗] {follower['username']} → {target} → {e}")

# ──────────────────────────────────────────────
# WORKER  (signup + login + posts for one user)
# ──────────────────────────────────────────────
def setup_user(user: dict) -> dict | None:
    """
    Full setup for a single user:
      1. Sign up
      2. Login → token
      3. Create 10 posts
    Returns {"username": ..., "token": ...} on success, None on failure.
    """
    if not signup(user):
        return None

    token = login(user)
    if not token:
        return None

    for i in range(1, 11):
        create_post(user["username"], token, i)

    return {"username": user["username"], "token": token}

# ──────────────────────────────────────────────
# MAIN
# ──────────────────────────────────────────────
def main():
    print("=" * 55)
    print("  Minigram Seed Script")
    print("=" * 55)

    # ── Phase 1: Signup + Login + Posts (parallel) ──
    print("\n[Phase 1] Creating users & posts in parallel...\n")
    results = []
    with concurrent.futures.ThreadPoolExecutor(max_workers=MAX_WORKERS) as executor:
        futures = {executor.submit(setup_user, user): user for user in USERS}
        for future in concurrent.futures.as_completed(futures):
            result = future.result()
            if result:
                results.append(result)

    all_usernames = [r["username"] for r in results]
    print(f"\n  ✓ {len(results)}/{len(USERS)} users ready.\n")

    # ── Phase 2: Follows (sequential per user, fast enough) ──
    print("[Phase 2] Creating follow relationships...\n")
    for r in results:
        user_dict = next(u for u in USERS if u["username"] == r["username"])
        follow_users(user_dict, r["token"], all_usernames)

    # ── Summary ──
    print("\n" + "=" * 55)
    print(f"  Seed complete.")
    print(f"  Users created : {len(results)}")
    print(f"  Posts created : ~{len(results) * 10}  (10 per user)")
    print(f"  Follows       : random (3–15 per user)")
    print("=" * 55)


if __name__ == "__main__":
    main()