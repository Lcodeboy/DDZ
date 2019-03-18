package com.game.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.game.dao.bean.DBPlayerBase;


public interface PlayerBaseMapper {

	@Select("SELECT * FROM t_playerbase")
	public List<DBPlayerBase> selectAllPlayer();

	@Select("SELECT * FROM t_playerbase WHERE playerid = #{playerid}")
	public DBPlayerBase selectPlayer(@Param("playerid") int playerid);

	@Select("SELECT * FROM t_playerbase WHERE playername = #{playerName}")
	public DBPlayerBase selectPlayerByName(@Param("playerName") String playerName);

	@Insert("INSERT INTO t_playerbase "
			+ "(playerid, playername, sex, head, birthday, city, createtime)"
			+ " VALUES "
			+ "(#{playerid}, #{playername}, #{sex}, #{head}, #{birthday}, #{city}, #{createtime} )")
	public int insert(
            @Param("playerid") int playerid,
            @Param("playername") String playername,
            @Param("sex") boolean sex,
            @Param("head") String head,
            @Param("birthday") String birthday,
            @Param("city") String city,
            @Param("createtime") long createtime
    );

	@Update("UPDATE t_playerbase SET playername = #{playername}, "
			+ "sex = #{sex}, head = #{head}, birthday = #{birthday}, "
			+ "city = #{city} WHERE playerid = #{playerid}")
	public int update(
            @Param("playerid") int playerid,
            @Param("playername") String playername,
            @Param("sex") boolean sex,
            @Param("head") String head,
            @Param("birthday") String birthday,
            @Param("city") String city
    );
	
	@Select("SELECT * FROM t_playerbase WHERE (createtime BETWEEN #{startTime} AND #{endTime}) AND "
			+"(playerid IN (SELECT playerid FROM t_playerzone WHERE createtime BETWEEN #{startTime} AND #{endTime} AND origin = #{origin}))")
	public List<DBPlayerBase> select1(
            @Param("startTime") long startTime,
            @Param("endTime") long endTime,
            @Param("origin") int origin);


	
}

