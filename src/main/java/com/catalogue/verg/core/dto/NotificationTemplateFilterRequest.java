package com.catalogue.verg.core.dto;

import lombok.Data;

@Data
public class NotificationTemplateFilterRequest {

    private int page = 0;
    private int size = 10;

    private String search="";

    private String status="All";

    private String module="All";

    private String receiver="All";
}