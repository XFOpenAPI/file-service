package com.xforceplus.openapi.api.application.service;

import com.xforceplus.openapi.api.infrastructure.util.CommonUtil;
import com.xforceplus.openapi.domain.entity.common.ErrorCodeDetailEntity;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FileApplicationService {

    public Either<ErrorCodeDetailEntity, byte[]> html2pdfStream(String htmlCode) {
        return CommonUtil.htmlToPdfStream(htmlCode, null);
    }
}
