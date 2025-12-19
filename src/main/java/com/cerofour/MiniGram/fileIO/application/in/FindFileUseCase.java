package com.cerofour.MiniGram.fileIO.application.in;

import java.net.URL;
import java.time.Duration;

public interface FindFileUseCase {

    /**
     * Get signed url of a filename for secure temporal sharing
     * @param parent Specifies where to find the file in the filesystem hierachy,
     *               can be a bucket name when implemented S3
     * @param key Specifies the key of the filename inside 'parent'
     * @param duration Duration of the signed url
     * @return signed url of file or null if it doesn't exists.
     */
    URL getSignedURLByFileKey(String parent, String key, Duration duration);
}
