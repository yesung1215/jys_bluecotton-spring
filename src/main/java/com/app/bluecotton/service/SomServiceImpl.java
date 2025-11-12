package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomReadResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.exception.SomException;
import com.app.bluecotton.mapper.SomImageMapper;
import com.app.bluecotton.mapper.SomMapper;
import com.app.bluecotton.repository.SomDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SomServiceImpl implements SomService {

    private final SomDAO somDAO;
    private final SomImageMapper somImageMapper;
    private final SomService somService;

    //  솜 등록
    @Override
    public void registerSom(SomVO somVO) {
        somDAO.save(somVO);
    }

    @Override
    public Integer selectSomMaxPage(Map<String, Object> map) {
        return somDAO.selectSomMaxPage(map);
    }

    //  솜 상세 조회
    @Override
    public SomReadResponseDTO findById(Long somId) {
        SomReadResponseDTO somResponseDTO = somDAO.findById(somId).map(SomReadResponseDTO::new).orElseThrow(() -> new SomException("솜을 불러오지 못했습니다"));
        List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(somId);
        if(somImages.size() == 0){
            SomImageVO somImageVO = new SomImageVO();
            somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
            somImageVO.setSomId(somId);
            somImageVO.setSomImageName("1762700261.jpg");
            somImages.add(somImageVO);
        }
        somResponseDTO.setSomCount(somService.selectAllSomJoinList(somId).size());
        somResponseDTO.setSomImageList(somImages);

        return somResponseDTO;
    }

    //  솜 전체 조회
    @Override
    public List<SomResponseDTO> findAllSom() {
        List<SomResponseDTO> somList = somDAO.findAllSom().stream().map((som) -> {
            SomResponseDTO somResponse = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(som.getId());
            if(somImages.size() == 0){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponse.setSomCount(somService.selectAllSomJoinList(som.getId()).size());
            somResponse.setSomImageList(somImages);
            return somResponse;
        }).toList();

        return somList;
    }

    @Override
    public List<SomResponseDTO> findByCategoryAndType(Map<String, Object> map){
        List<SomResponseDTO> somList = somDAO.findSomListByCategoryAndType(map).stream().map((som) -> {
            SomResponseDTO somResponse = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(som.getId());
            if(somImages.size() == 0){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponse.setSomCount(somService.selectAllSomJoinList(som.getId()).size());
            somResponse.setSomImageList(somImages);
            return somResponse;
        }).toList();

        return somList;
    }

    @Override
    public List<String> findAllAddress() {
        return somDAO.findAllSomAddress();
    }

    //  솜 좋아요
    @Override
    public void addLike(Long somId) {
        somDAO.addLike(somId);
    }

    //  솜 삭제
    @Override
    public void withdraw(Long somId) {
        somDAO.withdraw(somId);
    }

    @Override
    public void registerSomJoin(SomJoinVO somJoinVO) {
        somDAO.insertSomJoin(somJoinVO);
    }

    @Override
    public List<SomJoinResponseDTO> selectAllSomJoinList(Long somId){
        return somDAO.readSomJoinList(somId);
    }

    @Override
    public void deleteSomJoin(Long somJoinId) {
        somDAO.deleteSomJoin(somJoinId);
    }
}
