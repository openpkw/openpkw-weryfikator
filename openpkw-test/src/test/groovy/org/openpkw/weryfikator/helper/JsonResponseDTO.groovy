package org.openpkw.weryfikator.helper

class JsonResponseDTO {

    Integer status
    def jsonObject

    JsonResponseDTO(Integer status, jsonObject) {
        this.status = status
        this.jsonObject = jsonObject
    }
}
