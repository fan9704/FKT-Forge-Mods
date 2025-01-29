# Minecraft Forge Development Ch2 如何自製物品

---

## 新增自製物品骨幹

在專案 Main Class 下，新增一個名為 _item_ 的 Package 裡面再新增一個 _ModItems_ 的 Class

這個 ModItems Class 是用來定義你在這個模組中所有自定義的物品

在註冊自己的第一個物品之前，我們要知道在這個 _ModItems_ Class 要做甚麼事情
1. 在 _ModItems_ Class 中製作成可**延遲註冊**的 Class，目的是可以讓物品可以被添加到 Forge 之中
2. 製作一個**方便**自製的物品的 register 方法，協助註冊
3. 註冊實際自製物品。

----

### 1. 標記 ModItems 變成可延遲註冊物件

我們需要運用 _DeferredRegister_ 這個物件，他可以幫助我們將我們個人的物品後續註冊到 Forge 之中

```java
public class ModItems{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,ForgeTrain.MOD_ID);
}
```

----

### 2. 製作一個方便註冊物品的方法

```java
public class ModItems{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,ForgeTrain.MOD_ID);
    // 建造此 Mod 註冊到 Main Class 的方法
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
```