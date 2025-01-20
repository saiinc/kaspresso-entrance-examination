package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {

    private val storage = CerealStorageImpl(10f, 20f)

    @Test
    fun `should throw if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }
    @Test
    fun `should throw if cerealCapacity is smaller than containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(14f, 10f)
        }
    }
    @Test
    fun `should throw if addCereal amount is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.addCereal(Cereal.RICE,-1f)
        }
    }
    @Test
    fun `should throw if storage has full for new cereal`() {
        assertThrows(IllegalStateException::class.java) {
            storage.addCereal(Cereal.RICE, 20f)
            storage.addCereal(Cereal.RICE, 20f)
            storage.addCereal(Cereal.BULGUR, 20f)
            storage.addCereal(Cereal.PEAS, 20f)
        }
    }
    @Test
    fun `return the rest of amount of a cereal if container is full`() {
        //storage.addCereal(Cereal.RICE, 1f)
        assertEquals(15f, storage.addCereal(Cereal.RICE, 25f), 0.01f)
    }
    @Test
    fun `should throw if getCereal amount is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.RICE, -1f)
        }
    }
    @Test fun `returns the amount of cereal received`() {
        storage.addCereal(Cereal.RICE, 10f)
        assertEquals(5f, storage.getCereal(Cereal.RICE, 5f), 0.01f)
    }
    @Test fun `returns the remainder if the container had less`() {
        storage.addCereal(Cereal.RICE, 5f)
        assertEquals(5f, storage.getCereal(Cereal.RICE, 8f), 0.01f)
    }
    @Test fun `returns true if container successfully destroyed`() {
        storage.addCereal(Cereal.RICE, 5f)
        storage.getCereal(Cereal.RICE, 5f)
        assertEquals(true, storage.removeContainer(Cereal.RICE))
    }
    @Test fun `returns false if container is not empty` () {
        storage.addCereal(Cereal.RICE, 5f)
        assertEquals(false, storage.removeContainer(Cereal.RICE))
    }
    @Test fun `returns the amount of cereal stored in the container `() {
        storage.addCereal(Cereal.RICE, 5f)
        assertEquals(5f, storage.getAmount(Cereal.RICE), 0.01f)
    }
    @Test fun `returns the amount of cereal that a container can hold given its current fullness` () {
        storage.addCereal(Cereal.RICE, 8f)
        assertEquals(2f, storage.getSpace(Cereal.RICE), 0.01f)
    }
    @Test fun `returns text representation`() {
        storage.addCereal(Cereal.RICE, 2f)
        storage.addCereal(Cereal.PEAS, 4f)
        assertEquals("Рис: 2.0, Горох: 4.0", storage.toString())
    }
}