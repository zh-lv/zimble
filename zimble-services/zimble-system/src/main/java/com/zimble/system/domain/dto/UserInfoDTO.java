package com.zimble.system.domain.dto;

import com.zimble.system.domain.SysUser;

import java.io.Serializable;

public class UserInfoDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 用户基本信息
     */
    private SysUser sysUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;

    public SysUser getSysUser()
    {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser)
    {
        this.sysUser = sysUser;
    }

    public String[] getPermissions()
    {
        return permissions;
    }

    public void setPermissions(String[] permissions)
    {
        this.permissions = permissions;
    }

    public String[] getRoles()
    {
        return roles;
    }

    public void setRoles(String[] roles)
    {
        this.roles = roles;
    }
}
