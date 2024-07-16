package com.amancode.blogappserver.Payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentsResponse {

        
    private List<CommentsDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
    private boolean firstPage;
    private boolean emptyPage;
    
}
