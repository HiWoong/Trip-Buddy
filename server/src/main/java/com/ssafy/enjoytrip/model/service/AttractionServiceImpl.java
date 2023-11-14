package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.AttractionInfoDto;
import com.ssafy.enjoytrip.model.repo.AttractionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepo repo;

    @Override
    public List<AttractionInfoDto> search(int area, int type, String word) {

        // quicksort
        List<AttractionInfoDto> list = repo.search(area, type, word);

        for (AttractionInfoDto l : list) {
            double distance = Math.sqrt(Math.pow(Math.abs(37.0 - l.getLatitude()), 2) + Math.pow(Math.abs(127.0-l.getLongitude()), 2));
            l.setDistance(distance);
            System.out.println(l.getDistance());
        }

        return quickSort(list);
    }
	
	public static List<AttractionInfoDto> quickSort(List<AttractionInfoDto> items) {
        if (items.size() < 2) {
               return items;
        }

        AttractionInfoDto pivot = items.get(0);

        List<AttractionInfoDto> lower = new ArrayList<>();
        List<AttractionInfoDto> higher = new ArrayList<>();
        for (int i = 1; i < items.size(); i++) {
               if (items.get(i).compareTo(pivot) < 0) {
                      lower.add(items.get(i));
               } else {
                      higher.add(items.get(i));
               }
        }
        List<AttractionInfoDto> sorted = quickSort(lower);
        sorted.add(pivot);
        sorted.addAll(quickSort(higher));
        return sorted;
  }
}
