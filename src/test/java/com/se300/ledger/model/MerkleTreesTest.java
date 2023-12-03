// package com.se300.ledger.model;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Arrays;

// class MerkleTreesTest {

//     @Test
//     void testGetSHA2HexValueWithNull() {
//         // Arrange
//         MerkleTrees merkleTrees = new MerkleTrees(Arrays.asList("tx1", "tx2", "tx3"));

//         // Act & Assert
//         Exception exception = assertThrows(RuntimeException.class, () -> {
//             // This should cause a NullPointerException inside getSHA2HexValue
//             merkleTrees.getSHA2HexValue(null);
//         });

//         // The specific type of exception depends on how getSHA2HexValue handles the null input.
//         assertTrue(exception instanceof NullPointerException, "The exception should be a NullPointerException");
//     }
// }
