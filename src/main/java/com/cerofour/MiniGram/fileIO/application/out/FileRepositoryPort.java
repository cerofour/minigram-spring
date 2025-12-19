package com.cerofour.MiniGram.fileIO.application.out;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

public interface FileRepositoryPort {
    /**
     * Get signed url of a filename for secure temporal sharing
     * @param parent Specifies where to find the file in the filesystem hierachy,
     *               can be a bucket name when implemented S3
     * @param key Specifies the key of the filename inside 'parent'
     * @param duration Duration of the signed url
     * @return signed url of file or null if it doesn't exists.
     */
    URL getSignedURL(String parent, String key, Duration duration);

    /**
     * Uploads a file to the repository
     * @param parent Specifies where to store the file in the filesystem hierachy
     * @param key Specifies how to identify the file inside 'parent'
     * @param is Data
     */
    void upload(String parent, String key, InputStream is);
}
