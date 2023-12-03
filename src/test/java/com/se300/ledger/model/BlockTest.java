package com.se300.ledger.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBlockConstructor() {
        // Arrange
        Integer expectedBlockNumber = 1;
        String expectedPreviousHash = "0000000000000000";

        // Act
        Block block = new Block(expectedBlockNumber, expectedPreviousHash);

        // Assert
        assertEquals(expectedBlockNumber, block.getBlockNumber(), "Block number should be set from constructor.");
        assertEquals(expectedPreviousHash, block.getPreviousHash(), "Previous hash should be set from constructor.");
    }

    @Test
    void testSetBlockNumber() {
        // Arrange
        Block block = new Block(1, "0000000000000000");
        Integer newBlockNumber = 2;

        // Act
        block.setBlockNumber(newBlockNumber);

        // Assert
        assertEquals(newBlockNumber, block.getBlockNumber(), "Block number should match the set value.");
    }

    @Test
    void testSetAndGetPreviousHash() {
        // Arrange
        Block block = new Block(1, "0000000000000000");
        String newPreviousHash = "1111111111111111";

        // Act
        block.setPreviousHash(newPreviousHash);

        // Assert
        assertEquals(newPreviousHash, block.getPreviousHash(), "Previous hash should match the set value.");
    }

    @Test
    void testSetAndGetPreviousBlock() {
        // Arrange
        Block currentBlock = new Block(2, "0000000000000000");
        Block previousBlock = new Block(1, "1111111111111111");

        // Act
        currentBlock.setPreviousBlock(previousBlock);

        // Assert
        assertEquals(previousBlock, currentBlock.getPreviousBlock(), "Previous block should match the set block.");
    }
}
