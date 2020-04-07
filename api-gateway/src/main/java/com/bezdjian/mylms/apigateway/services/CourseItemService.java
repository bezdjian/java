package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseItemService {

  private final CourseService courseService;
  private final ItemService itemService;

  @Autowired
  public CourseItemService(CourseService courseService, ItemService itemService) {
    this.courseService = courseService;
    this.itemService = itemService;
  }

  public Map<List<String>, List<String>> allCourseItems() {

    Collection<CourseDTO> courses = courseService.findAllCourses();
    Collection<ItemDTO> items = itemService.topBrands();
    Map<List<String>, List<String>> map = new HashMap<>();

    List<String> courseNames = new ArrayList<>();
    List<String> itemNames = new ArrayList<>();

    courses.forEach(c -> courseNames.add(c.getCoursename()));
    items.forEach(i -> itemNames.add(i.getName()));

    map.put(courseNames, itemNames);

    return map;
  }
}
