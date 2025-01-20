package ru.webrelab.kie.cerealstorage


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
        if (storage[cereal] == null) {
            check(storage.keys.size < maxContainers) {
                "Хранилище не позволяет разместить ещё один контейнер для новой крупы"
            }
            if (amount <= containerCapacity) {
                storage[cereal] = amount
                return 0f
            }
            else {
                storage[cereal] = containerCapacity
                return amount - containerCapacity
            }
        }
        else {
            if (amount <= containerCapacity) {
                storage[cereal] = storage[cereal]!! + amount
                return 0f
            }
            else {
                val cerealRest = storage[cereal]
                storage[cereal] = amount
                return amount - containerCapacity + cerealRest!!
            }
        }
    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        require(amount >= 0) {
            "Получение крупы не может быть отрицательным"
        }
        if (amount > storage[cereal]!!) {
            val cerealRest = storage[cereal]!!
            storage[cereal] = 0f
            return cerealRest
        }
        else {
            storage[cereal] = storage[cereal]!! - amount
            return amount
        }
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        if (storage[cereal] == 0f) {
            storage.remove(cereal)
            return true
        }
        else {
            return false
        }
    }

    override fun getAmount(cereal: Cereal): Float {
        return storage[cereal]!!
    }

    override fun getSpace(cereal: Cereal): Float {
        return containerCapacity - storage[cereal]!!
    }

    override fun toString(): String {
        var text = ""
        for (container in storage) {
            text += container.key.local + ": " + container.value.toString() + ", "
        }
        return text.slice(0..text.length - 3)
    }
}
