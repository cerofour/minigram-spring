#!/bin/bash
echo "Creating Minigram S3 bucket..."

awslocal s3 mb s3://minigram-media --region us-east-1

# Optional: Set CORS so your Angular frontend can read images directly
awslocal s3api put-bucket-cors --bucket minigram-media --cors-configuration '{
  "CORSRules": [{
    "AllowedOrigins": ["http://localhost:4200"],
    "AllowedMethods": ["GET", "PUT", "POST", "DELETE"],
    "AllowedHeaders": ["*"]
  }]
}'

echo "Bucket created successfully."