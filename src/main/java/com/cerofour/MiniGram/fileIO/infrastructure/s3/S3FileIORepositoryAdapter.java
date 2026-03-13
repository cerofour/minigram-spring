package com.cerofour.MiniGram.fileIO.infrastructure.s3;

import com.cerofour.MiniGram.fileIO.application.out.FileRepositoryPort;
import com.cerofour.MiniGram.shared.infrastructure.FileUtils;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class S3FileIORepositoryAdapter implements FileRepositoryPort {

    private final S3Template s3Template;

    @Value(value = "${spring.cloud.aws.s3.bucket}")
    private final String s3BucketName = "minigram-s3-bucket";

    @Override
    public URL getSignedURL(String key, Duration duration) {
        return s3Template.createSignedGetURL(s3BucketName, key, duration);
    }

    @Override
    public void upload(String key, String filename, InputStream is) {
        s3Template.upload(s3BucketName, key, is, ObjectMetadata.builder()
                        .contentType(FileUtils.getContentType(filename))
                        .build());
    }
}
