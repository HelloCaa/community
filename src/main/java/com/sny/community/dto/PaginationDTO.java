package com.sny.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//用于完成分页查询数据的传送类
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    //根据数据库中的问题数计算总页数，并将要显示的页面初始化
    public void setPagination(Integer totalCount, Integer size, Integer page) {
         this.page =page;
         if(totalCount % size == 0){
             totalPage = totalCount / size;
             if(totalCount == 0){
                 totalPage = 1;
             }
         } else {
             totalPage = totalCount / size + 1;
         }

        if(page < 1){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

         pages.add(page);
         for(int i = 1; i <= 3; i++){
             if(page - i > 0){
                 pages.add(0, page - i);
             }

             if(page + i <= totalPage){
                 pages.add(page + i);
             }
         }

         //是否展示上一页按钮
         if(page == 1){
             showPrevious = false;
         } else {
             showPrevious = true;
         }

         //是否展示下一页按钮
         if(page == totalPage){
             showNext = false;
         } else {
             showNext = true;
         }

         //是否展示第一页按钮
         if(pages.contains(1)){
             showFirstPage = false;
         } else {
             showFirstPage = true;
         }

         //是否展示最后一页按钮
        if(pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
