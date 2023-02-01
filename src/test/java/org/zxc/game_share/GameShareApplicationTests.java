package org.zxc.game_share;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.*;
import org.zxc.game_share.mapper.GameMapper;
import org.zxc.game_share.mapper.GenresMapper;
import org.zxc.game_share.mapper.UserMapper;
import org.zxc.game_share.service.UserService;

import java.util.Date;
import java.util.List;

@SpringBootTest
class GameShareApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GenresMapper genresMapper;

	@Autowired
	private GameMapper gameMapper;

	@Test
	public void testFindUserByUid(){
		User u = userMapper.findUserByUid(1);
		System.out.println(u);
	}

	/**
	 * 测试根据游戏主键 gid 查询游戏
	 */
	@Test
	public void testFindGameByGid(){
		Game game = gameMapper.findGameByGid(1, 0);
		System.out.println(game);
	}

	/**
	 * 测试根据游戏名称模糊查询游戏有多少条数据
	 */
	@Test
	public void testFindTotalCountByName(){
//		int i = gameMapper.findTotalCountByName("", isDeleted);
//		System.out.println(i);
	}

	/**
	 * 测试根据游戏类别ggId查询游戏记录数
	 */
	@Test
	public void testFindTotalCountByggId(){
		int num = gameMapper.findTotalCountByggId(1);
		System.out.println(num);
	}

	/**
	 * 测试根据游戏类别 id 查询游戏信息
	 */
	@Test
	public void testFindGameByggId(){

		List<Game> gameList = gameMapper.findGameByggId(0, 10, 1);
		for (Game g: gameList){
			System.out.println(g);
		}
	}

	@Test
	void contextLoads() {
	}

	/**
	 * 测试查询所有用户信息
	 */
	@Test
	void testUserFindAll(){
		List<User> userList = userService.findAll();
		for (User user: userList){
			System.out.println(user);
		}
	}

	/**
	 * 测试分页模糊查询用户信息
	 * 经过测试，这里发现， 不官name是null还是空字符串，都可以查询出结果
	 * 空字符串，
	 * 结果都能够查询出来
	 */
	@Test
	void testUserFindByUsernameWithPage(){
		List<User> userList = userMapper.findByUsernameWithPage(0, 10, "", 0);
		for (User user: userList){
			System.out.println(user);
		}
	}

	/**
	 * 测试用户添加功能
	 */
	@Test
	void testUserAdd(){
		User user = new User();
		user.setUsername("关羽");
		user.setSex("男");
		user.setAge(13);
		user.setGmtCreate(new Date());
		user.setPassword("gy");
		user.setIsDeleted(0);
		Role role = new Role();
		role.setRId(2);
		user.setRole(role);
		System.out.println(user);
		int i = userMapper.addUser(user);
		System.out.println(i);
	}

	/**
	 * 测试查询游戏分类
	 */
	@Test
	void testGenres(){
		List<Genres> genresList = genresMapper.findByggNameWithPage(0, 10, "");
		for (Genres genres: genresList){
			System.out.println(genres);
		}
	}
}
