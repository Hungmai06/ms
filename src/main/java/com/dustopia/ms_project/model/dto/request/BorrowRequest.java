package com.dustopia.ms_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    private String readerId;              // ID người mượn
    private String librarianId;           // ID thủ thư thực hiện
    private List<String> bookIds;         // Danh sách ID sách cần mượn
    private LocalDateTime expectedReturnTime; // Thời gian trả dự kiến
    private Float deposit;              // Tiền đặt cọc (nếu có)
    private String note;                // Ghi chú thêm (nếu có)
}
