package com.example.study2.basic1.memberteam;

import com.example.study2.basic1.member.Member;
import com.example.study2.basic1.team.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member_team")
public class MemberTeam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	Team team;

}
