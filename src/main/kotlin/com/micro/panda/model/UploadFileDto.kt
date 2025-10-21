package com.micro.panda.model

import com.micro.panda.model.dto.AccountDto

data class UploadFileDto(
    val type: ImportType = ImportType.IMPORT,
    val json: List<AccountDto>,
)

enum class ImportType() {
    IMPORT,
    REPLACE;
}

