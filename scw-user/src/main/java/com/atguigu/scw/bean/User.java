package com.atguigu.scw.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@ApiModel(value="用户实体类")
public class User {
	@ApiModelProperty("用户序列化，主键")
	private Integer id;
	@ApiModelProperty("用户账号")
	private String username;
	@ApiModelProperty("用户密码")
	private String password;
	
}
