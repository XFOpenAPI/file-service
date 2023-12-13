package com.xforceplus.openapi.api.interfaces.facade.api;

import com.xforceplus.openapi.api.application.service.FileApplicationService;
import com.xforceplus.openapi.domain.entity.common.ErrorCodeDetailEntity;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.xforceplus.openapi.domain.constant.OpenApiConstant.TRACE_ID;

@RestController
@RequestMapping("fileconversion/{tenantCode}")
@Slf4j
@AllArgsConstructor
public class FileApi {

    private final FileApplicationService fileApplicationService;

    @PostMapping(value = "/file/v1/files/action/html2pdf-stream", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> html2pdfByStream(@RequestPart("htmlFile") MultipartFile file) {

        String traceId = MDC.get(TRACE_ID);
        InputStream inputStream = null;
        String htmlCode = null;

        try {
            inputStream = file.getInputStream();
            htmlCode = IOUtils.toString(inputStream);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().getBytes(StandardCharsets.UTF_8));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        try {
            htmlCode = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().getBytes(StandardCharsets.UTF_8));
        }

        Either<ErrorCodeDetailEntity, byte[]> either = fileApplicationService.html2pdfStream(htmlCode);

        if (either.isLeft()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(either.getLeft().getErrorMessage().getBytes(StandardCharsets.UTF_8));
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(either.get());
        }
    }
}
