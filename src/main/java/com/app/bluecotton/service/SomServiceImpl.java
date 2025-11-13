package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.MemberSomLeaderResponseDTO;
import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomReadResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomLikeVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.exception.SomException;
import com.app.bluecotton.repository.SomDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SomServiceImpl implements SomService {

    private final SomDAO somDAO;
    private final SomImageService somImageService;
    private final MemberService memberService;

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
    public SomResponseDTO findById(Long somId, String memberEmail) {
        SomResponseDTO somResponseDTO = somDAO.findById(somId).map(SomResponseDTO::new).orElseThrow(() -> new SomException("솜을 불러오지 못했습니다"));
        List<SomImageVO> somImages = somImageService.selectImagesBySomId(somId);
        Long currentMemberId = memberService.getMemberIdByMemberEmail(memberEmail);
        SomLikeVO somLikeVO = new SomLikeVO();
        somLikeVO.setSomId(somId);
        somLikeVO.setMemberId(currentMemberId);
        if(somImages.isEmpty()){
            SomImageVO somImageVO = new SomImageVO();
            somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
            somImageVO.setSomId(somId);
            somImageVO.setSomImageName("1762700261.jpg");
            somImages.add(somImageVO);
        }
        somResponseDTO.setSomLike(somDAO.selectSomLikeCount(somId));
        somResponseDTO.setIsSomLike(somDAO.selectIsSomLike(somLikeVO));
        somResponseDTO.setMemberSomLeader(new MemberSomLeaderResponseDTO(memberService.getMemberById(somResponseDTO.getMemberId())));
        somResponseDTO.setSomCount(somDAO.readSomJoinList(somId).size());
        somResponseDTO.setSomJoinList(somDAO.readSomJoinList(somId));
        somResponseDTO.setSomImageList(somImages);

        return somResponseDTO;
    }

    //  솜 전체 조회
    @Override
    public List<SomResponseDTO> findAllSom() {
        List<SomResponseDTO> somList = somDAO.findAllSom().stream().map((som) -> {
            SomResponseDTO somResponseDTO = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageService.selectImagesBySomId(som.getId());
            if(somImages.isEmpty()){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponseDTO.setMemberSomLeader(new MemberSomLeaderResponseDTO(memberService.getMemberById(somResponseDTO.getMemberId())));
            somResponseDTO.setSomLike(somDAO.selectSomLikeCount(som.getId()));
            somResponseDTO.setSomCount(somDAO.readSomJoinList(som.getId()).size());
            somResponseDTO.setSomJoinList(somDAO.readSomJoinList(som.getId()));
            somResponseDTO.setSomImageList(somImages);
            return somResponseDTO;
        }).toList();

        return somList;
    }

    @Override
    public List<SomResponseDTO> findByCategoryAndType(Map<String, Object> map){
        List<SomResponseDTO> somList = somDAO.findSomListByCategoryAndType(map).stream().map((som) -> {
            SomResponseDTO somResponseDTO = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageService.selectImagesBySomId(som.getId());
            Long currentMemberId = memberService.getMemberIdByMemberEmail(map.get("memberEmail").toString());
            SomLikeVO somLikeVO = new SomLikeVO();
            somLikeVO.setSomId(som.getId());
            somLikeVO.setMemberId(currentMemberId);
            if(somImages.isEmpty()){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponseDTO.setSomLike(somDAO.selectSomLikeCount(som.getId()));
            somResponseDTO.setIsSomLike(somDAO.selectIsSomLike(somLikeVO));
            somResponseDTO.setMemberSomLeader(new MemberSomLeaderResponseDTO(memberService.getMemberById(somResponseDTO.getMemberId())));
            somResponseDTO.setSomCount(somDAO.readSomJoinList(som.getId()).size());
            somResponseDTO.setSomJoinList(somDAO.readSomJoinList(som.getId()));
            somResponseDTO.setSomImageList(somImages);
            return somResponseDTO;
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

    @Override
    public void insertSomLike(SomLikeVO somLikeVO){
        somDAO.insertSomLike(somLikeVO);
    }

    @Override
    public Integer selectSomLikeCount(Long somId){
        return somDAO.selectSomLikeCount(somId);
    }

    @Override
    public Boolean selectIsSomLike(SomLikeVO somLikeVO) {
        return somDAO.selectIsSomLike(somLikeVO);
    }

    @Override
    public void deleteSomLike(SomLikeVO somLikeVO) {
        somDAO.deleteSomLike(somLikeVO);
    }
}
