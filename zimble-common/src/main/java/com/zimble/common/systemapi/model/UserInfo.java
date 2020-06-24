package com.zimble.common.systemapi.model;

import com.zimble.common.systemapi.domain.dto.SysUserDTO;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 用户基本信息
     */
    private SysUserDTO sysUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;

    public SysUserDTO getSysUser()
    {
        return sysUser;
    }

    public void setSysUser(SysUserDTO sysUser)
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
