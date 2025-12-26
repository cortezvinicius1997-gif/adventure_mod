package com.cortez.adventuremod.client.screen.custom;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.client.widget.MultilineTextFieldWidget;
import com.cortez.adventuremod.network.SaveProgramPacket;
import com.cortez.adventuremod.screen.custom.ProgrammingScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ProgrammingScreen extends HandledScreen<ProgrammingScreenHandler> {

    private MultilineTextFieldWidget textField;
    private ButtonWidget programButton;
    private ItemStack lastStack = ItemStack.EMPTY;

    // Widgets para a tela de nome
    private TextFieldWidget nameField;
    private TextFieldWidget descriptionField;
    private ButtonWidget confirmButton;
    private ButtonWidget backButton;

    // Estado da tela (true = tela principal, false = tela de nome)
    private boolean showingMainScreen = true;

    public static final Identifier GUI_TEXTURE =
            Identifier.of(AdventureMod.MODID, "textures/gui/programming/program_gui.png");

    public ProgrammingScreen(ProgrammingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Inicializa ambas as telas
        initMainScreen(x, y);
        initNameScreen(x, y);

        // Carrega o código DEPOIS de inicializar todos os widgets
        loadExistingCode();

        // Mostra apenas a tela principal inicialmente
        updateScreenVisibility();
    }

    /**
     * Inicializa os widgets da tela principal
     */
    private void initMainScreen(int x, int y) {
        textField = new MultilineTextFieldWidget(
                textRenderer,
                x + 10,
                y + 20,
                155,
                90,
                Text.literal("Digite aqui")
        );

        textField.setMaxLength(5000);
        textField.setEditable(true);

        addSelectableChild(textField);
        setInitialFocus(textField);

        // REMOVA a chamada loadExistingCode() daqui

        // Adicionar botão "Programar"
        programButton = ButtonWidget.builder(
                        Text.translatable("block.adventuremod.programming_block.screen.program_btn"),
                        button -> onProgramButtonClick()
                )
                .dimensions(x + 10, y + 115, 60, 20)
                .build();

        addDrawableChild(programButton);
    }

    /**
     * Inicializa os widgets da tela de nome
     */
    private void initNameScreen(int x, int y) {
        // Campo de texto para o nome
        nameField = new TextFieldWidget(
                textRenderer,
                x + 10,
                y + 30,
                155,
                20,
                Text.literal("Name")
        );
        nameField.setPlaceholder(Text.translatable("block.adventuremod.programming_block.screen.input_name_placeholder"));
        nameField.setMaxLength(50);

        addSelectableChild(nameField);

        // Campo de texto para a descrição
        descriptionField = new TextFieldWidget(
                textRenderer,
                x + 10,
                y + 60,
                155,
                20,
                Text.literal("Description")
        );
        descriptionField.setPlaceholder(Text.translatable("Descrição (opcional)"));
        descriptionField.setMaxLength(100);

        addSelectableChild(descriptionField);

        // Botão Confirmar
        confirmButton = ButtonWidget.builder(
                        Text.translatable("block.adventuremod.programming_block.screen.confirm_btn"),
                        button -> onConfirmButtonClick()
                )
                .dimensions(x + 10, y + 90, 70, 20)
                .build();

        addDrawableChild(confirmButton);

        // Botão Voltar
        backButton = ButtonWidget.builder(
                        Text.translatable("block.adventuremod.programming_block.screen.back_btn"),
                        button -> onBackButtonClick()
                )
                .dimensions(x + 85, y + 90, 70, 20)
                .build();

        addDrawableChild(backButton);
    }

    /**
     * Atualiza a visibilidade dos widgets baseado no estado da tela
     */
    private void updateScreenVisibility() {
        if (showingMainScreen) {
            // Mostra tela principal
            textField.visible = true;
            programButton.visible = true;

            // Esconde tela de nome
            nameField.setVisible(false);
            descriptionField.setVisible(false);
            confirmButton.visible = false;
            backButton.visible = false;

            setFocused(textField);
        } else {
            // Esconde tela principal
            textField.visible = false;
            programButton.visible = false;

            // Mostra tela de nome e descrição
            nameField.setVisible(true);
            descriptionField.setVisible(true);
            confirmButton.visible = true;
            backButton.visible = true;

            setFocused(nameField);
        }
    }

    /**
     * Carrega o código existente do item no slot e preenche o textField
     */
    private void loadExistingCode() {
        ItemStack stack = handler.getSlot(0).getStack();

        if (!stack.isEmpty()) {
            lastStack = stack.copy();
            loadCodeFromStack(stack);
        } else {
            AdventureMod.LOGGER.info("Nenhum item no slot para carregar código.");
        }
    }

    private void onProgramButtonClick() {
        // Troca para a tela de nome
        showingMainScreen = false;
        updateScreenVisibility();
    }

    private void onConfirmButtonClick() {
        String name = nameField.getText();
        String description = descriptionField.getText();



        AdventureMod.LOGGER.info("Nome confirmado: " + name);
        AdventureMod.LOGGER.info("Descrição: " + (description.isEmpty() ? "Nenhuma" : description));

        String code = textField.getText();

        // Verifica se o código não é vazio ou nulo
        if (code == null || code.trim().isEmpty()) {
            AdventureMod.LOGGER.warn("Código vazio, não será salvo.");
            return;
        }

        AdventureMod.LOGGER.info("Enviando código para o servidor: " + code);

        // Envia o packet para o servidor (você precisará atualizar o SaveProgramPacket para incluir a descrição)
        ClientPlayNetworking.send(new SaveProgramPacket(code, name, description));

        // Mensagem de feedback para o player
        if (client != null && client.player != null) {
            client.player.sendMessage(
                    Text.literal("Código salvo com sucesso!"),
                    true // actionBar = true (mostra acima da hotbar)
            );
        }

        // Volta para a tela principal
        onBackButtonClick();
    }

    private void onBackButtonClick() {
        // Limpa os campos
        nameField.setText("");
        descriptionField.setText("");

        // Volta para a tela principal
        showingMainScreen = true;
        updateScreenVisibility();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        if (showingMainScreen) {
            context.drawText(textRenderer, title, 8, 6, 0x404040, false);
        } else {
            context.drawText(textRenderer, Text.translatable("block.adventuremod.programming_block.screen.config_label"), 8, 6, 0x404040, false);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        if (showingMainScreen) {
            // Verifica se o item no slot mudou
            checkForItemChange();
            textField.render(context, mouseX, mouseY, delta);
        } else {
            nameField.render(context, mouseX, mouseY, delta);
            descriptionField.render(context, mouseX, mouseY, delta);
        }

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    /**
     * Verifica se o item no slot mudou e carrega o novo código
     */
    private void checkForItemChange() {
        ItemStack currentStack = handler.getSlot(0).getStack();

        // Verifica se o item mudou (comparação por referência e conteúdo)
        if (!ItemStack.areEqual(lastStack, currentStack)) {
            lastStack = currentStack.copy();

            if (!currentStack.isEmpty()) {
                loadCodeFromStack(currentStack);
            } else {
                // Se o slot ficou vazio, limpa o textField
                textField.setText("");
                AdventureMod.LOGGER.info("Slot vazio, textField limpo.");
            }
        }
    }

    /**
     * Carrega o código de um ItemStack específico
     */
    private void loadCodeFromStack(ItemStack stack) {
        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (customData != null) {
            NbtCompound nbt = customData.copyNbt();

            if (nbt.contains("program_code")) {
                String code = nbt.getString("program_code");
                textField.setText(code);
                AdventureMod.LOGGER.info("Código carregado automaticamente: " + code);
            } else {
                textField.setText("");
                AdventureMod.LOGGER.info("Item sem código, textField limpo.");
            }

            // Carrega o nome do programa
            if (nbt.contains("program_name")) {
                String name = nbt.getString("program_name");
                nameField.setText(name);
                AdventureMod.LOGGER.info("Nome carregado automaticamente: " + name);
            } else {
                nameField.setText("");
            }

            // Carrega a descrição do programa
            if (nbt.contains("program_description")) {
                String description = nbt.getString("program_description");
                descriptionField.setText(description);
                AdventureMod.LOGGER.info("Descrição carregada automaticamente: " + description);
            } else {
                descriptionField.setText("");
            }
        } else {
            textField.setText("");
            nameField.setText("");
            descriptionField.setText("");
            AdventureMod.LOGGER.info("Item sem dados customizados, campos limpos.");
        }
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (showingMainScreen) {
            return textField.charTyped(chr, keyCode) || super.charTyped(chr, keyCode);
        } else {
            return nameField.charTyped(chr, keyCode)
                    || descriptionField.charTyped(chr, keyCode)
                    || super.charTyped(chr, keyCode);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (showingMainScreen) {
            // Se o TextField está focado, intercepta a tecla do inventário
            if (textField.isFocused()) {
                // Verifica se é a tecla do inventário (geralmente 'E')
                if (this.client != null && this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    // Permite que o TextField processe a tecla como um caractere normal
                    return textField.keyPressed(keyCode, scanCode, modifiers);
                }
            }
            return textField.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            // Na tela de nome/descrição, intercepta a tecla do inventário
            if (nameField.isFocused() || descriptionField.isFocused()) {
                // Verifica se é a tecla do inventário (geralmente 'E')
                if (this.client != null && this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    // Permite que os campos processem a tecla como um caractere normal
                    if (nameField.isFocused()) {
                        return nameField.keyPressed(keyCode, scanCode, modifiers);
                    } else {
                        return descriptionField.keyPressed(keyCode, scanCode, modifiers);
                    }
                }
            }
            return nameField.keyPressed(keyCode, scanCode, modifiers)
                    || descriptionField.keyPressed(keyCode, scanCode, modifiers)
                    || super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        if (showingMainScreen) {
            // Se o TextField está focado, ESC apenas remove o foco ao invés de fechar a tela
            if (textField.isFocused()) {
                setFocused(null);
                return false;
            }
        } else {
            // Na tela de nome, ESC volta para a tela principal
            onBackButtonClick();
            return false;
        }
        return super.shouldCloseOnEsc();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (showingMainScreen) {
            if (textField.mouseClicked(mouseX, mouseY, button)) {
                setFocused(textField);
            }
            return textField.mouseClicked(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
        } else {
            if (nameField.mouseClicked(mouseX, mouseY, button)) {
                setFocused(nameField);
                return true;
            }
            if (descriptionField.mouseClicked(mouseX, mouseY, button)) {
                setFocused(descriptionField);
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (showingMainScreen) {
            return textField.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)
                    || super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public String getTextFieldContent() {
        return textField.getText();
    }

    public String getNameFieldContent() {
        return nameField.getText();
    }

    public String getDescriptionFieldContent() {
        return descriptionField.getText();
    }
}