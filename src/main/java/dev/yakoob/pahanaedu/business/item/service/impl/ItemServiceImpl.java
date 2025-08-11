package dev.yakoob.pahanaedu.business.item.service.impl;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;
import dev.yakoob.pahanaedu.business.item.mapper.ItemMapper;
import dev.yakoob.pahanaedu.business.item.model.Item;
import dev.yakoob.pahanaedu.business.item.service.ItemService;
import dev.yakoob.pahanaedu.persistence.item.dao.ItemDAO;
import dev.yakoob.pahanaedu.persistence.item.dao.impl.ItemDAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemServiceImpl implements ItemService {

    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public void saveItem(ItemDTO item) {
        item.setItemCode(UUID.randomUUID().toString());
        itemDAO.save(ItemMapper.toEntity(item));
    }

    @Override
    public ItemDTO getItemByCode(String itemCode) {
        return ItemMapper.toDTO(itemDAO.findById(itemCode));
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        List<Item> itemList = itemDAO.findAll();
        for (Item item : itemList) {
            itemDTOS.add(ItemMapper.toDTO(item));
        }
        return itemDTOS;
    }

    @Override
    public void updateItem(String itemCode, ItemDTO itemDTO) {
        itemDAO.update(itemCode, ItemMapper.toEntity(itemDTO));
    }

    @Override
    public void deleteItem(String itemCode) {
        itemDAO.delete(itemCode);
    }

    @Override
    public int getItemCount() {
        return itemDAO.getCount();
    }
}
