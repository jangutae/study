package com.example.study2.member;

import java.util.ArrayList;
import java.util.List;

import com.example.study2.memberteam.MemberTeam;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "members")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	Integer age;

	// Team Entity 와 1 : N 연관관계 시 맵핑
	// @OneToMany(mappedBy = "member")
	// List<Team> teamList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<MemberTeam> memberTeamList = new ArrayList<>();
}
