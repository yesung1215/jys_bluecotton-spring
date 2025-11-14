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
    private final ChatService chatService;
    private final ChatMemberService chatMemberService;

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
        SomResponseDTO somResponseDTO = somDAO.findById(somId).orElseThrow(() -> new SomException("솜을 불러오지 못했습니다"));
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
        somResponseDTO.setIsSomLike(somDAO.selectIsSomLike(somLikeVO));
        somResponseDTO.setMemberSomLeader(new MemberSomLeaderResponseDTO(memberService.getMemberById(somResponseDTO.getMemberId())));
        somResponseDTO.setSomJoinList(somDAO.readSomJoinList(somId));
        somResponseDTO.setSomImageList(somImages);

        return somResponseDTO;
    }

    //  솜 전체 조회
    @Override
    public List<SomResponseDTO> findAllSom() {
        List<SomResponseDTO> somList = somDAO.findAllSom().stream().map((som) -> {
            List<SomImageVO> somImages = somImageService.selectImagesBySomId(som.getId());
            if(somImages.isEmpty()){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            som.setSomCount(somDAO.readSomJoinList(som.getId()).size());
            som.setSomJoinList(somDAO.readSomJoinList(som.getId()));
            som.setSomImageList(somImages);
            return som;
        }).toList();

        return somList;
    }

    @Override
    public List<SomResponseDTO> findByCategoryAndType(Map<String, Object> map){
        List<SomResponseDTO> somList = somDAO.findSomListByCategoryAndType(map).stream().map((som) -> {
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
            som.setIsSomLike(somDAO.selectIsSomLike(somLikeVO));
            som.setMemberSomLeader(new MemberSomLeaderResponseDTO(memberService.getMemberById(som.getMemberId())));
            som.setSomJoinList(somDAO.readSomJoinList(som.getId()));
            som.setSomImageList(somImages);
            return som;
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
        // 1. 솜 참여 처리
        somDAO.insertSomJoin(somJoinVO);
        
        // 2. 솜 정보 조회 (채팅방 제목을 위해)
        SomResponseDTO somInfo = somDAO.findById(somJoinVO.getSomId())
                .orElseThrow(() -> new SomException("솜 정보를 찾을 수 없습니다."));
        
        // 3. 해당 솜의 채팅방 찾기 (채팅방 제목 = 솜 제목)
        Long chatId = chatService.getChatIdByTitle(somInfo.getSomTitle());
        
        // 4. 채팅방이 존재하면 자동으로 채팅방에 참여
        if (chatId != null) {
            com.app.bluecotton.domain.vo.chat.ChatMemberVO chatMemberVO = 
                    new com.app.bluecotton.domain.vo.chat.ChatMemberVO();
            chatMemberVO.setChatId(chatId);
            chatMemberVO.setMemberId(somJoinVO.getMemberId());
            chatMemberVO.setChatMemberRole("USER");
            chatMemberVO.setChatMemberStatus("ACTIVE");
            
            // 채팅방 참여 (이미 참여 중이면 중복 방지됨)
            Integer existingCount = chatMemberService.exists(chatMemberVO);
            if (existingCount == 0) {
                chatMemberService.createChatMember(chatMemberVO);
                log.info("솜 참여 시 채팅방 자동 참여 완료 - chatId: {}, memberId: {}", chatId, somJoinVO.getMemberId());
            } else {
                log.info("이미 채팅방에 참여 중 - chatId: {}, memberId: {}", chatId, somJoinVO.getMemberId());
            }
        }
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
