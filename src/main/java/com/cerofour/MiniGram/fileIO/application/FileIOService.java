package com.cerofour.MiniGram.fileIO.application;

import com.cerofour.MiniGram.fileIO.application.in.FindFileUseCase;
import com.cerofour.MiniGram.fileIO.application.in.UploadFileUseCase;
import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.shared.domain.UseCase;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

@UseCase
@RequiredArgsConstructor
public class FileIOService implements FindFileUseCase, UploadFileUseCase {

    private final FileRepositoryPort repositoryPort;

    /**
     *
     * @param parent Specifies where to find the file in the filesystem hierachy,
     *               can be a bucket name when implemented S3
     * @param key Specifies the key of the filename inside 'parent'
     * @param duration Duration of the signed url
     * @return
     */
    @Override
    public URL getSignedURLByFileKey(String parent, String key, Duration duration) {
        return repositoryPort.getSignedURL(parent, key, duration);
    }

    /**
     *
     * @param parent Specifies where to store the file in the filesystem hierachy
     * @param key Specifies how to identify the file inside 'parent'
     * @param filename
     * @param is Data
     */
    @Override
    public void uploadWithIS(String parent, String key, String filename, InputStream is) {
        repositoryPort.upload(parent, key, filename, is);
    }
}
