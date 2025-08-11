package dev.yakoob.pahanaedu.business.item.service;

import dev.yakoob.pahanaedu.business.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    void saveItem(ItemDTO item);

    ItemDTO getItemByCode(String itemCode);

    List<ItemDTO> getAllItems();

    void updateItem(String itemCode, ItemDTO itemDTO);

    void deleteItem(String itemCode);

    int getItemCount();

}
