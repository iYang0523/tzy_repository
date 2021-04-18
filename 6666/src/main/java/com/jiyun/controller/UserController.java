package com.jiyun.controller;

import com.jiyun.Service.UserService;
import com.jiyun.pojo.Address;
import com.jiyun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@Controller
public class UserController {
@Autowired
    private UserService userService;

    @RequestMapping("/findall")
    public String find(User user,Integer pageNums, Model model) {
        Integer pageNum=0;
    if(pageNums!=null && pageNums>0){
        pageNum=pageNums;
    }
        String name = user.getName();
        String dizhi = user.getDizhi();
      Date birthday = user.getBirthday();


        Pageable pageList  = PageRequest.of(pageNum , 2);
    Page<User> fin=userService.findall(name,birthday,dizhi,pageList);

//    System.out.println("分页信息：");
//    System.out.println("总记录数："+fin.getTotalElements()+",总页数："+fin.getTotalPages());
//    System.out.println("页码："+(fin.getNumber()+1)+",每页条数："+fin.getSize());

   model.addAttribute("name",name);  // 姓名回显
   model.addAttribute("dizhi",dizhi); //地址回显
    model.addAttribute("birthday",birthday); //地址回显

model.addAttribute("pageInfo",fin);
    model.addAttribute("st",fin);

    model.addAttribute("Total",fin.getTotalElements());  //总记录数
        model.addAttribute("TotalPages",fin.getTotalPages()); // 总页数
        model.addAttribute("Number",fin.getNumber()); //页码
        model.addAttribute("Size",fin.getSize()); // 每页条数
    return "show";
}
// 省市
@RequestMapping("/add")
    public String add(Model model){
    Integer oid=0;
    List<Address> list=userService.findByAid(oid);

    model.addAttribute("list",list);
    return "add";
}
// 查省市
@RequestMapping("/sss")
@ResponseBody
    public List<Address> ss(int oid){
List<Address> li =userService.fff(oid);
return li;
}
//去页面
@RequestMapping("/find")
    public String fi(){
        return "redirect:/findall";
}

// 添加
@RequestMapping("/Tadd")
@ResponseBody
    public String Addcon(@RequestBody User user){
    System.out.println(user);
    Integer shengid = user.getShengid();
    Integer shiid = user.getShiid();
    Integer xianid = user.getXianid();
    String dizhi = user.getDizhi();

//    String dizhi = user.getDizhi();
    Address ad=userService.findBySid(shengid);
    Address bySid = userService.findBySid(shiid);
    Address xian = userService.findBySid(xianid);
    System.out.println(ad);
    user.setSheng(ad.getTname());
    user.setShi(bySid.getTname());
    user.setXian(xian.getTname());
   user.setDizhi(ad.getTname()+bySid.getTname()+xian.getTname()+dizhi);
   try {
       userService.add(user);
       return "1";
   }catch (Exception e){
      e.printStackTrace();
       return "2";
   }



}

// 统计
@RequestMapping("/tongji")
    public String to(Model model){
        String n="1";
        Integer nan=userService.findsex(n);
    String v="2";
    Integer nv=userService.findsex(v);
    model.addAttribute("nan",nan);
    model.addAttribute("nv",nv);
        return "tongji";
}

//退出
    @RequestMapping("/tuichu")
    public String tui(){
        return "redirect:/findall";
    }

@RequestMapping("/dele")
    public String del(int id){
        Long pp=(long)id;
        userService.del(pp);
        return "redirect:/findall";
}

@RequestMapping("/upda")
    public String up(int id,Model model){
    Integer oid=0;
    List<Address> list=userService.findByAid(oid);

    model.addAttribute("list",list);



    Long pp=(long)id;
     User user=userService.upda(pp);
     model.addAttribute("user",user);
        return "update";
}

@RequestMapping("/edit")
    public String edi(User user){
    Integer shengid = user.getShengid();
    Integer shiid = user.getShiid();
    Integer xianid = user.getXianid();
    String dizhi = user.getDizhi();
    Address ad=userService.findBySid(shengid);
    Address bySid = userService.findBySid(shiid);
    Address xian = userService.findBySid(xianid);
    user.setSheng(ad.getTname());
    user.setShi(bySid.getTname());
    user.setXian(xian.getTname());
   user.setDizhi(ad.getTname()+bySid.getTname()+xian.getTname()+dizhi);
    userService.edix(user);
//    userService.del(id);
    return "redirect:/findall";
}

}
