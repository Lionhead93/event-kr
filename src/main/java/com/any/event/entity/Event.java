package com.any.event.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name="EVENT")
public class Event implements Serializable{

    @Id
    @Column(name = "EVENT_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventSeq;

    @Column(name = "EVENT_ID", nullable = false)
    private Long eventId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EVENT_DATE_LOCAL")
    private LocalDateTime eventDateLocal;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "REG_DATE", nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
