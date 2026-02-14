package com.yjh107.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Data   // 클래스에 대한 Getter, Setter, toString, equals, hashCode 메서드 자동 생성
@NoArgsConstructor  // 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull    // 수식하는 필드에 null을 할당하려는 시도에 대해 NullPointerException 발생
    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name ="completed")
    private boolean completed;

    @Column(
            nullable = false,
            name = "created_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
