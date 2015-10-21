package org.openpkw.utils.csv.json;

public class DokumentGeneratorRequest {

    private String templateName;
    private PeripheryVote formData;

    public DokumentGeneratorRequest(String templateName, PeripheryVote formData) {
        this.templateName = templateName;
        this.formData = formData;
    }

    public String getTemplateName() {
        return templateName;
    }

    public PeripheryVote getFormData() {
        return formData;
    }
}