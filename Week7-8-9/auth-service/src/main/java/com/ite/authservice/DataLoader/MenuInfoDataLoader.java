//package com.ite.authservice.DataLoader;
//
//import com.ite.authservice.entities.MenuInfo;
//import com.ite.authservice.repositories.MenuInfoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class MenuInfoDataLoader implements CommandLineRunner {
//
//    private final MenuInfoRepository menuInfoRepository;
//
//    @Autowired
//    public MenuInfoDataLoader(MenuInfoRepository menuInfoRepository) {
//        this.menuInfoRepository = menuInfoRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<MenuInfo> menuInfos = Arrays.asList(
//                new MenuInfo("1", "permissionId1", "admin/dashboard", "admin/trang-chu", "/admin/dashboard", 1, "Dashboard", null),
//                new MenuInfo("2", "permissionId2", "admin/dashboard", "admin/trang-chu", "/admin/user-manage", 1, "User", null),
//                new MenuInfo("4", "permissionId4", "", "attach_money", "", 1, "Quản lý giao dịch", Arrays.asList(
//                        new MenuInfo("401", "permissionId401", "/admin/giao-dich-thanh-toan", "payment", "/admin/giao-dich-thanh-toan", 2, "Thanh toán", null),
//                        new MenuInfo("402", "permissionId402", "/admin/giao-dich-hoan-tra", "keyboard_return", "/admin/giao-dich-hoan-tra", 2, "Hoàn trả", null)
//                ))
//        );
//
//        menuInfoRepository.saveAll(menuInfos);
//    }
//}