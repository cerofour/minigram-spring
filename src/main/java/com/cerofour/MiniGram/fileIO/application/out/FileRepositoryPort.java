package com.cerofour.MiniGram.fileIO.application.out;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;

public interface FileRepositoryPort {
    /**
     * Get signed url of a filename for secure temporal sharing
     * @param key Specifies the key of the filename inside 'parent'
     * @param duration Duration of the signed url
     * @return signed url of file or null if it doesn't exists.
     */
    URL getSignedURL(String key, Duration duration);

    /**
     * Uploads a file to the repository
     * @param key Specifies how to identify the file inside 'parent'
     * @param is Data
     */
    void upload(String key, String filename, InputStream is);
}
