package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * .
 * Created by yuheng.lin.
 * Date : 2017/5/5
 */
@Table(name = "mm_member")
public class Member implements Serializable{
    private static final long serialVersionUID = -1L;
    @Id
    private Long memberId;
    private String memberType;
    private Long marketId;
    private String status;
    private String brNumber;
    @Transient //该注解将忽略字段和属性,不用持久化到数据库
    private String aa;
    @Column(name = "dob")
    private Date bb;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrNumber() {
        return brNumber;
    }

    public void setBrNumber(String brNumber) {
        this.brNumber = brNumber;
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public Date getBb() {
        return bb;
    }

    public void setBb(Date bb) {
        this.bb = bb;
    }
}
