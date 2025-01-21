package ru.webrelab.kie.cerealstorage

import java.lang.Float.min


class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {

    /**
     * Блок инициализации класса.
     * Выполняется сразу при создании объекта
     */
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()
    private val maxContainers = (storageCapacity / containerCapacity).toInt()

    override fun addCereal(cereal: Cereal, amount: Float): Float {
        require(amount >= 0) {
            "Дабавление крупы не может быть отрицательным"
        }
        check(!((storage.keys.size >= maxContainers) && (storage[cereal] == null))) {
            "Хранилище не позволяет разместить ещё один контейнер для новой крупы"
        }
        val cerealRest = storage[cereal] ?: 0f
        val toAddCereal = min(amount + cerealRest, containerCapacity)
        storage[cereal] = toAddCereal
        return amount + cerealRest - toAddCereal
    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        require(amount >= 0) {
            "Получение крупы не может быть отрицательным"
        }
        val cerealRest = storage[cereal] ?: return 0f
        val toGetCereal = min(amount, cerealRest)
        storage[cereal] = cerealRest - toGetCereal
        return toGetCereal
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        return storage.remove(cereal, 0f)
    }

    override fun getAmount(cereal: Cereal): Float {
        return storage[cereal] ?: 0f
    }

    override fun getSpace(cereal: Cereal): Float {
        val cerealRest = storage[cereal] ?: return 0f
        return containerCapacity - cerealRest
    }

    override fun toString(): String {
        var text = ""
        for (container in storage) {
            text += container.key.local + ": " + container.value.toString() + ", "
        }
        return text.slice(0..text.length - 3)
    }
}
