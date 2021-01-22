package com.ssafy.doit.service;

import com.ssafy.doit.model.Feed;
import com.ssafy.doit.model.request.ResponseFeed;
import com.ssafy.doit.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl {

    private final FeedRepository feedRepository;

    @Transactional
    public ResponseFeed findAllByGroup(Long groupPk){
        Feed entity = feedRepository.findAllByGroup(groupPk).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. groupPK" + groupPk));
        return new ResponseFeed(entity);
    }
}
