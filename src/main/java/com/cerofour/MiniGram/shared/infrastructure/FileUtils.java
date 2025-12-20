package com.cerofour.MiniGram.shared.infrastructure;

import java.util.Optional;

public class FileUtils {

    public static Optional<String> getFileExtension(String filename) {
        if (filename == null || filename.isBlank()) {
            return Optional.empty();
        }
        String cleanName = filename.trim();
        int lastDotIndex = cleanName.lastIndexOf('.');
        if (lastDotIndex <= 0 || lastDotIndex == cleanName.length() - 1) {
            return Optional.empty();
        }
            return Optional.of(
                    cleanName.substring(lastDotIndex + 1).toLowerCase());
    }

    public static String getContentType(String filename) {
        return FileUtils.getFileExtension(filename)
                .map((ext) -> {
                    if (ext.equals("jpg") || ext.equals("jpeg"))
                        return "image/jpeg";

                    else if (ext.equals("png"))
                        return "image/png";

                    return "application/octet-stream";
                })
                .orElse("application/octet-stream");
    }

    private FileUtils() {}
}
