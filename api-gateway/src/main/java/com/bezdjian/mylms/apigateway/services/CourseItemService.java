package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.model.Course;
import com.bezdjian.mylms.apigateway.model.Item;
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

    Collection<Course> courses = courseService.findAllCourses();
    Collection<Item> items = itemService.topBrands();
    Map<List<String>, List<String>> map = new HashMap<>();

    List<String> courseNames = new ArrayList<>();
    List<String> itemNames = new ArrayList<>();

    courses.forEach(c -> courseNames.add(c.getCourseName()));
    items.forEach(i -> itemNames.add(i.getName()));

    map.put(courseNames, itemNames);

    return map;
  }
}
