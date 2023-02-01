package com.nhaccuaquang.musique.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetail {

    @Id
    @Column(name = "account_detail_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    private String name;
}
