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

    @Override
    public URL getSignedURLByFileKey(String parent, String key, Duration duration) {
        return repositoryPort.getSignedURL(parent, key, duration);
    }

    @Override
    public void uploadWithIS(String parent, String key, String filename, InputStream is) {
        repositoryPort.upload(parent, key, filename, is);
    }
}
