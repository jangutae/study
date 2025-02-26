package com.example.study2.team;

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
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	// Team Entity 와 1 : N 연관관계 시 맵핑
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "member_id")
	// Member member;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	List<MemberTeam> memberTeamList = new ArrayList<>();
}
