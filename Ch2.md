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

---

### 3. 註冊實際的物件

```java
public class ModItems{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,ForgeTrain.MOD_ID);

    // 註冊物品
    public static final RegistryObject<Item> ALEXANDRITE = ITEMS.register("alexandrite",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ALEXANDRITE = ITEMS.register("raw_alexandrite",
            () -> new Item(new Item.Properties()));
    // 建造此 Mod 註冊到 Main Class 的方法
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
```

---

## 將 _ModItems_ 自製的 Class 註冊到 Main Class

在 ForgeTrain(Main Class) 的**建構子**加入

```java
    public ForgeTrain()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        // 將 ModItems 註冊到 Main Class 裡面
        ModItems.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
```

----

### 將物品添加到物品欄之中

也是在 ForgeTrain(Main Class) 中，其中的 _addCreative_ 方法，

下列的程式碼會將 **Alexandrite 跟 Raw Alexandrite** 添加到 **材料** 的物品欄中

```java
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        // 添加 ModItem ALEXANDRITE/RAW_ALEXANDRITE 至 "Ingredient" 物品欄
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ALEXANDRITE);
            event.accept(ModItems.RAW_ALEXANDRITE);
        }
    }
```

---

## 物品資源設定

物品資源有幾個主要的步驟

1. 放置物品的圖片(textures)
2. 物品名稱/翻譯 設定(lang)

接下來這些都屬於非程式，純資源的設定，因此都會放置在 _resources_ 資料夾之中

----

### 放置物品的圖片(textures)

建立資料夾

```shell
/src/main/resources/assets/{MOD_ID}/textures
# 範例 /src/main/resources/assets/fkttestrainmod/textures
```

新增放置物品圖片的資料夾

```shell
/src/main/resources/assets/{MOD_ID}/textures/item
# 範例 /src/main/resources/assets/fkttestrainmod/textures/item
```

接者在裡面放置 _alexandrite.png_ 與 _raw_alexandrite.png_ 屬於剛剛新增物品所需要的圖片

----

### 物品名稱/翻譯 設定(lang)

建立資料夾

```shell
/src/main/resources/assets/{MOD_ID}/textures
# 範例 /src/main/resources/assets/fkttestrainmod/textures
```

新增語言翻譯檔案

我們打算做繁體中文與英文版，因此要新增兩個 JSON 檔案 _en_us.json_ 與 _zh_tw.json_

#### en_us.json 英文設定檔案

```json
{
  "item.fkttestrainmod.alexandrite": "Alexandrite Gem",
  "item.fkttestrainmod.raw_alexandrite": "Raw Alexandrite"
}
```

#### zh_tw.json 繁體中文設定檔案

```json
{
  "item.fkttestrainmod.alexandrite": "亞歷山大寶石",
  "item.fkttestrainmod.raw_alexandrite": "亞歷山大原石"
}
```

---

## 啟動遊戲

用 gradle runClient 即可看到自己製作的新物品