package com.dustopia.ms_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRequest {
    private String librarianId;// ID thủ thư tiếp nhận trả
    private LocalDateTime returnTime;       // Thời gian trả thực tế
    private List<String> borrowedBookIds;     // Danh sách ID của BorrowedBook đang được trả
    private String returnNote;              // Ghi chú thêm (nếu có)
    private List<DamageRequest> damages;    // Danh sách sách bị hỏng (nếu có)
}
