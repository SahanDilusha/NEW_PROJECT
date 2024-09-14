package contrroler;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validations;
import dto.ProductDTO;
import dto.Response_DTO;
import entity.BrandEntity;
import entity.ConditionEntity;
import entity.MainCategoryEntity;
import entity.ProductEntity;
import entity.ProductSatusEntity;
import entity.SubCategoryEntity;
import entity.UserEntity;
import java.io.InputStream;
import org.hibernate.Session;

@MultipartConfig
@WebServlet(name = "AddNewProduct", urlPatterns = {"/AddNewProduct"})
public class AddNewProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        ProductDTO productDto = gson.fromJson(req.getParameter("product"), ProductDTO.class);

        System.out.println(productDto.getDescription());

        System.out.println(productDto.getBarndId());

        Part mainImgPart = req.getPart("main_image");

        Part mainImgPart1 = req.getPart("image_1");

        Part mainImgPart2 = req.getPart("imain_2");

        System.out.println(productDto.getTitile());

        Response_DTO responseDto = new Response_DTO();

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (productDto.getTitile().trim().isEmpty()) {
            responseDto.setContent("Product name is required.");
        } else if (productDto.getTitile().length() > 150) {
            responseDto.setContent("Product name max length is 150 characters.");
        } else if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            responseDto.setContent("Description is required.");
        } else if (!Validations.isInt(String.valueOf(productDto.getQty())) || productDto.getQty() <= 0) {
            responseDto.setContent("Quantity must be a positive integer.");
        } else if (!Validations.isDouble(String.valueOf(productDto.getPrice())) || productDto.getPrice() <= 0) {
            responseDto.setContent("Please enter a valid price.");
        } else if (productDto.isShippingAdd() && productDto.getShipping() <= 0) {
            responseDto.setContent("Please enter a valid shipping price.");
        } else if (productDto.getBarndId() <= 0) {
            responseDto.setContent("Please select a valid brand.");
        } else if (session.load(BrandEntity.class, productDto.getBarndId()) == null) {
            responseDto.setContent("Selected brand is not valid.");
        } else if (productDto.getMainCategoryId() <= 0) {
            responseDto.setContent("Please select a main category.");
        } else if (session.load(MainCategoryEntity.class, productDto.getMainCategoryId()) == null) {
            responseDto.setContent("Selected main category is not valid.");
        } else if (productDto.getSubCategoryId() <= 0) {
            responseDto.setContent("Please select a sub-category.");
        } else if (session.load(SubCategoryEntity.class, productDto.getSubCategoryId()) == null) {
            responseDto.setContent("Selected sub-category is not valid.");
        } else if (productDto.getConditionId() <= 0) {
            responseDto.setContent("Please select a valid condition.");
        } else if (session.load(ConditionEntity.class, productDto.getConditionId()) == null) {
            responseDto.setContent("Selected condition is not valid.");
        } else if (mainImgPart == null || mainImgPart.getSize() == 0) {
            responseDto.setContent("Main image is required.");
        } else {

            ProductEntity newProduct = new ProductEntity();
            newProduct.setTitile(productDto.getTitile());
            newProduct.setDescription(productDto.getDescription());
            newProduct.setQty(productDto.getQty());
            newProduct.setBarnd((BrandEntity) session.load(BrandEntity.class, productDto.getBarndId()));
            newProduct.setMainCategory((MainCategoryEntity) session.load(MainCategoryEntity.class, productDto.getMainCategoryId()));
            newProduct.setSubCategory((SubCategoryEntity) session.load(SubCategoryEntity.class, productDto.getSubCategoryId()));
            newProduct.setCondition_id((ConditionEntity) session.load(ConditionEntity.class, productDto.getConditionId()));
            newProduct.setShipping(productDto.isShippingAdd() ? productDto.getShipping() : 0.00);
            newProduct.setPrice(productDto.getPrice());
            newProduct.setProductSatus(new ProductSatusEntity(1));
            newProduct.setUser(new UserEntity(2));
            newProduct.setDate(new Date());
            newProduct.setRateit(0);

            int id = (int) session.save(newProduct);

            String applicationPath = req.getServletContext().getRealPath("");
            String newApplicationPath = applicationPath.replace("build" + File.separator + "web", "web");

            File folder = new File(newApplicationPath + File.separator + "product-images" + File.separator + id);
            folder.mkdir();

            File file1 = new File(folder, "image1.png");
            InputStream inputStream = mainImgPart.getInputStream();
            Files.copy(inputStream, file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

            File file2 = new File(folder, "image2.png");
            InputStream inputStream2 = mainImgPart1.getInputStream();
            Files.copy(inputStream2, file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

            File file3 = new File(folder, "image3.png");
            InputStream inputStream3 = mainImgPart2.getInputStream();
            Files.copy(inputStream3, file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

            session.beginTransaction().commit();
            responseDto.setStatus(true);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseDto));
        System.out.println(gson.toJson(responseDto));

    }

}
