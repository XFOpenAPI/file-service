package com.xforceplus.openapi.api.infrastructure.util;

import com.openhtmltopdf.extend.FSSupplier;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.xforceplus.openapi.domain.entity.common.ErrorCodeDetailEntity;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class CommonUtil {

    private static Either<ErrorCodeDetailEntity, String> htmlToPdfFile(String html, String  oss) {

        String randomName = "PDF-" + System.currentTimeMillis() + ".pdf";
        OutputStream os = null;
        try {
            Document document = Jsoup.parse(html, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

            Path path = Paths.get(randomName);
            os = Files.newOutputStream(path);
            String baseUrl = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri().toURL().toString();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(randomName);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), baseUrl);

            builder.useFont(new FSSupplier<InputStream>() {
                @Override
                public InputStream supply() {
                    return getClass().getClassLoader().getResourceAsStream("fonts/Kaiti.ttf");
                }
            }, "Kaiti");

            builder.useFont(new FSSupplier<InputStream>() {
                @Override
                public InputStream supply() {
                    return getClass().getClassLoader().getResourceAsStream("fonts/arialuni.ttf");
                }
            }, "Arial Unicode MS");

            builder.useFont(new FSSupplier<InputStream>() {
                @Override
                public InputStream supply() {
                    return getClass().getClassLoader().getResourceAsStream("fonts/simsun.ttf");
                }
            }, "SimSun");

            builder.run();
            os.close();
            return Either.right(randomName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorCodeDetailEntity.of("SYSTEM9999", "未知错误:" + e.getMessage(), ""));
        } finally {
            if(os != null){
                try {
                    os.close();
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static Either<ErrorCodeDetailEntity, byte[]> htmlToPdfStream(String htmlCode, String oss) {

        Either<ErrorCodeDetailEntity, String> either = htmlToPdfFile(htmlCode, oss);
        if (either.isLeft()) {
            return Either.left(either.getLeft());
        }

        try {
            Path path = Paths.get(either.get());
            byte[] fileData = FileUtils.readFileToByteArray(path.toFile());
            return Either.right(fileData);
        } catch (IOException e) {
            return Either.left(ErrorCodeDetailEntity.of("SYSTEM9999", "未知错误:" + e.getMessage(), ""));
        } finally {
            try {
                Files.deleteIfExists(Paths.get(either.get()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
