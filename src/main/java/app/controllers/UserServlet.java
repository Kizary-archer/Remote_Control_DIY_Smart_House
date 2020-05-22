package app.controllers;

import app.builder.UserBuilder;
import app.builder.UserViewBuilder;
import app.builder.UserRoleBuilder;
import app.entityes.UserRoleEntity;
import app.entityes.UserStatusEntity;
import app.entityes.UsersEntity;
import app.entityes.UserviewEntity;
import app.services.UserRoleService;
import app.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/DTUserView","/DTUserRoleView","/admin/addUser","/viewUser","/delUser","/updUser"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        UserRoleService userRoleService = new UserRoleService();
        UserService userService = new UserService();
        List<UserRoleEntity> userRoleEntityList = (List<UserRoleEntity>) userRoleService.getUserRole(0,0,new UserRoleEntity());
        List<UserStatusEntity> userStatusEntityList = (List<UserStatusEntity>) userService.getUserStatus();
        request.setAttribute("roles",userRoleEntityList);
        request.setAttribute("statuses",userStatusEntityList);
       if(request.getServletPath().equals("/admin/addUser")) {
           requestDispatcher = request.getRequestDispatcher("/view/addUser.jsp");
       }else {
            UsersEntity usersEntity = userService.getUserById(Long.valueOf(request.getParameter("idUser")));
            request.setAttribute("user", usersEntity);
            requestDispatcher = request.getRequestDispatcher("view/viewUser.jsp");
        }
        assert requestDispatcher != null;
        requestDispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        UserService userService = new UserService();
        UserRoleService userRoleService = new UserRoleService();
        UsersEntity usersEntity = null;
        try {
            usersEntity = new UserBuilder(request).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(request.getServletPath().equals("/DTUserView")) {
            UserviewEntity userviewEntity = null;
            try {
                userviewEntity = new UserViewBuilder(request).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<UserviewEntity> userviewEntityList = (List<UserviewEntity>) userService.getUsersView(1000,0,userviewEntity);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            String json = gson.toJson(userviewEntityList);
            response.getWriter().write(json);
        }

        if(request.getServletPath().equals("/DTUserRoleView")) {
            UserRoleEntity userRoleEntity = null;
            try {
                userRoleEntity = new UserRoleBuilder(request).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<UserRoleEntity> userRoleEntityList = (List<UserRoleEntity>) userRoleService.getUserRole(1000,0,userRoleEntity);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            String json = gson.toJson(userRoleEntityList);
            response.getWriter().write(json);
        }

       if(request.getServletPath().equals("/admin/addUser")) {
           if (userService.addUser(usersEntity)) {
               request.setAttribute("isUserAdded", "true");
           }
           else request.setAttribute("isUserAdded", "false");
           doGet(request, response);
        }
/*
        if(request.getServletPath().equals("/searchHouse")) {
            HouseView houseView = null;
            try {
                houseView = new HouseViewBuilder(request).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<HouseView> houseEntityList = (List<HouseView>) apartmentService.getHouses(1000,0,houseView);
            String json = new Gson().toJson(houseEntityList);
            response.getWriter().write(json);
        }

        if(request.getServletPath().equals("/DTApart")) {
            List<ApartmentEntity> apartmentEntityList = (List<ApartmentEntity>) apartmentService.getApartmentsList(1000,0,apartmentEntity);
            Gson gson = new GsonBuilder()
                                        .excludeFieldsWithoutExposeAnnotation()
                                        .create();
            String json = gson.toJson(apartmentEntityList);
            response.getWriter().write(json);
        }

        if(request.getServletPath().equals("/updApartment")) {
            if (apartmentService.updApartment(apartmentEntity)) {
                request.setAttribute("isApartmentUpd", "true");
            }
            else {
                request.setAttribute("isApartmentUpd", "false");
            }
            doGet(request,response);
        }
        if(request.getServletPath().equals("/delApartment")) {
            if (apartmentService.delApartment(apartmentEntity)) {
                request.setAttribute("isApartmentdel", "true");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
            }
            else {
                request.setAttribute("isApartmentdel", "false");
                doGet(request, response);
            }
        }
        if(request.getServletPath().equals("/listApartments")) {
            List<ApartmentEntity> apartmentEntityList = new ArrayList<ApartmentEntity>(apartmentService.getApartmentsList(1000, 0,apartmentEntity));
            request.setAttribute("apartments",apartmentEntityList);
            doGet(request, response);
        }*/
    }
}


