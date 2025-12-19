package com.cerofour.MiniGram.fileIO.application.in;

import java.io.InputStream;

public interface UploadFileUseCase {

    /**
     * Uploads a file to 'parent' inside the repository
     * @param parent Specifies where to store the file in the filesystem hierachy
     * @param key Specifies how to identify the file inside 'parent'
     * @param is Data
     */
    void uploadWithIS(String parent, String key, InputStream is);
}
