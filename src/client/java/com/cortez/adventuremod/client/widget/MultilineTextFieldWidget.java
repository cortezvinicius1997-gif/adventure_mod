package com.cortez.adventuremod.client.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class MultilineTextFieldWidget extends ClickableWidget {

    private final TextRenderer textRenderer;
    private final List<String> lines;
    private int cursorLine;
    private int cursorColumn;
    private int maxLength;
    private boolean editable;
    private int focusedTicks;
    private int scrollOffset;
    private int maxVisibleLines;

    // Selection support
    private boolean hasSelection = false;
    private int selectionStartLine;
    private int selectionStartColumn;
    private int selectionEndLine;
    private int selectionEndColumn;

    // Scroll bar
    private static final int SCROLLBAR_WIDTH = 6;
    private boolean isDraggingScrollbar = false;
    private int scrollbarDragStartY = 0;
    private int scrollbarDragStartOffset = 0;

    // Word wrap support
    private List<WrappedLine> wrappedLines;
    private int textAreaWidth;

    private static class WrappedLine {
        int logicalLine;      // Qual linha lógica esta linha visual representa
        int startColumn;      // Onde começa na linha lógica
        int endColumn;        // Onde termina na linha lógica
        String text;          // Texto desta linha visual

        WrappedLine(int logicalLine, int startColumn, int endColumn, String text) {
            this.logicalLine = logicalLine;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
            this.text = text;
        }
    }

    public MultilineTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(x, y, width, height, text);
        this.textRenderer = textRenderer;
        this.lines = new ArrayList<>();
        this.lines.add("");
        this.cursorLine = 0;
        this.cursorColumn = 0;
        this.maxLength = 5000;
        this.editable = true;
        this.scrollOffset = 0;
        this.maxVisibleLines = (height - 6) / 10;
        this.textAreaWidth = width - SCROLLBAR_WIDTH - 8;
        this.wrappedLines = new ArrayList<>();
        updateWrappedLines();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getText() {
        return String.join("\n", lines);
    }

    public void setText(String text) {
        lines.clear();
        // Remove carriage returns antes de processar
        text = text.replace("\r", "");
        String[] splitLines = text.split("\n", -1);
        for (String line : splitLines) {
            lines.add(line);
        }
        if (lines.isEmpty()) {
            lines.add("");
        }
        cursorLine = 0;
        cursorColumn = 0;
        scrollOffset = 0;
        clearSelection();
        updateWrappedLines();
    }

    private void updateWrappedLines() {
        wrappedLines.clear();

        for (int lineIdx = 0; lineIdx < lines.size(); lineIdx++) {
            String line = lines.get(lineIdx);

            if (line.isEmpty()) {
                wrappedLines.add(new WrappedLine(lineIdx, 0, 0, ""));
                continue;
            }

            int start = 0;
            while (start < line.length()) {
                String remaining = line.substring(start);
                int width = textRenderer.getWidth(remaining);

                if (width <= textAreaWidth) {
                    // Linha cabe inteira
                    wrappedLines.add(new WrappedLine(lineIdx, start, line.length(), remaining));
                    break;
                } else {
                    // Precisa quebrar
                    int end = start + 1;
                    while (end < line.length() && textRenderer.getWidth(line.substring(start, end + 1)) <= textAreaWidth) {
                        end++;
                    }

                    // Tenta quebrar em espaço
                    int lastSpace = line.lastIndexOf(' ', end - 1);
                    if (lastSpace > start) {
                        end = lastSpace + 1;
                    }

                    wrappedLines.add(new WrappedLine(lineIdx, start, end, line.substring(start, end)));
                    start = end;
                }
            }
        }

        // Atualiza maxVisibleLines baseado nas linhas visuais
        maxVisibleLines = (height - 6) / 10;
    }

    private int getVisualLineForCursor() {
        for (int i = 0; i < wrappedLines.size(); i++) {
            WrappedLine wl = wrappedLines.get(i);
            if (wl.logicalLine == cursorLine && cursorColumn >= wl.startColumn && cursorColumn <= wl.endColumn) {
                return i;
            }
        }
        return 0;
    }

    private void clearSelection() {
        hasSelection = false;
        selectionStartLine = 0;
        selectionStartColumn = 0;
        selectionEndLine = 0;
        selectionEndColumn = 0;
    }

    private String getSelectedText() {
        if (!hasSelection) return "";

        int startLine = Math.min(selectionStartLine, selectionEndLine);
        int endLine = Math.max(selectionStartLine, selectionEndLine);
        int startCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionStartColumn : selectionEndColumn;
        int endCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionEndColumn : selectionStartColumn;

        if (selectionStartLine == selectionEndLine && selectionStartColumn > selectionEndColumn) {
            int temp = startCol;
            startCol = endCol;
            endCol = temp;
        }

        StringBuilder selected = new StringBuilder();

        if (startLine == endLine) {
            selected.append(lines.get(startLine).substring(startCol, endCol));
        } else {
            selected.append(lines.get(startLine).substring(startCol));
            for (int i = startLine + 1; i < endLine; i++) {
                selected.append("\n").append(lines.get(i));
            }
            selected.append("\n").append(lines.get(endLine).substring(0, endCol));
        }

        return selected.toString();
    }

    private void deleteSelection() {
        if (!hasSelection) return;

        int startLine = Math.min(selectionStartLine, selectionEndLine);
        int endLine = Math.max(selectionStartLine, selectionEndLine);
        int startCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionStartColumn : selectionEndColumn;
        int endCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionEndColumn : selectionStartColumn;

        if (selectionStartLine == selectionEndLine && selectionStartColumn > selectionEndColumn) {
            int temp = startCol;
            startCol = endCol;
            endCol = temp;
        }

        if (startLine == endLine) {
            String line = lines.get(startLine);
            lines.set(startLine, line.substring(0, startCol) + line.substring(endCol));
        } else {
            String firstPart = lines.get(startLine).substring(0, startCol);
            String lastPart = lines.get(endLine).substring(endCol);

            for (int i = endLine; i > startLine; i--) {
                lines.remove(i);
            }

            lines.set(startLine, firstPart + lastPart);
        }

        cursorLine = startLine;
        cursorColumn = startCol;
        clearSelection();
        updateWrappedLines();
        adjustScroll();
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!visible) return;

        // Fundo
        context.fill(getX(), getY(), getX() + width, getY() + height, 0xFF000000);
        context.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, 0xFF2B2B2B);

        int textX = getX() + 4;
        int textY = getY() + 4;

        // Renderizar linhas visuais
        int endLine = Math.min(scrollOffset + maxVisibleLines, wrappedLines.size());
        for (int i = scrollOffset; i < endLine; i++) {
            WrappedLine wl = wrappedLines.get(i);
            int y = textY + (i - scrollOffset) * 10;

            // Renderizar seleção se houver
            if (hasSelection) {
                renderSelectionForWrappedLine(context, wl, textX, y);
            }

            context.drawText(textRenderer, wl.text, textX, y, 0xE0E0E0, false);
        }

        // Renderizar cursor
        if (isFocused() && editable && !hasSelection) {
            focusedTicks++;
            if (focusedTicks / 6 % 2 == 0) {
                int visualLine = getVisualLineForCursor();
                if (visualLine >= scrollOffset && visualLine < scrollOffset + maxVisibleLines) {
                    WrappedLine wl = wrappedLines.get(visualLine);
                    int relativeColumn = cursorColumn - wl.startColumn;
                    String textBeforeCursor = wl.text.substring(0, Math.min(relativeColumn, wl.text.length()));

                    int cursorX = textX + textRenderer.getWidth(textBeforeCursor);
                    int cursorY = textY + (visualLine - scrollOffset) * 10;

                    context.fill(cursorX, cursorY - 1, cursorX + 1, cursorY + 9, 0xFFD0D0D0);
                }
            }
        }

        // Renderizar scrollbar
        if (wrappedLines.size() > maxVisibleLines) {
            renderScrollbar(context, mouseX, mouseY);
        }
    }

    private void renderSelectionForWrappedLine(DrawContext context, WrappedLine wl, int textX, int textY) {
        int startLine = Math.min(selectionStartLine, selectionEndLine);
        int endLine = Math.max(selectionStartLine, selectionEndLine);
        int startCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionStartColumn : selectionEndColumn;
        int endCol = (selectionStartLine < selectionEndLine ||
                (selectionStartLine == selectionEndLine && selectionStartColumn <= selectionEndColumn))
                ? selectionEndColumn : selectionStartColumn;

        if (selectionStartLine == selectionEndLine && selectionStartColumn > selectionEndColumn) {
            int temp = startCol;
            startCol = endCol;
            endCol = temp;
        }

        // Verifica se esta linha visual está dentro da seleção
        if (wl.logicalLine < startLine || wl.logicalLine > endLine) return;

        int selStart = 0;
        int selEnd = wl.text.length();

        if (wl.logicalLine == startLine) {
            selStart = Math.max(0, startCol - wl.startColumn);
        }
        if (wl.logicalLine == endLine) {
            selEnd = Math.min(wl.text.length(), endCol - wl.startColumn);
        }

        if (selStart < selEnd) {
            String beforeSelection = wl.text.substring(0, selStart);
            String selection = wl.text.substring(selStart, selEnd);

            int selectionX = textX + textRenderer.getWidth(beforeSelection);
            int selectionWidth = textRenderer.getWidth(selection);

            context.fill(selectionX, textY - 1, selectionX + selectionWidth, textY + 9, 0x993333FF);
        }
    }

    private void renderScrollbar(DrawContext context, int mouseX, int mouseY) {
        int scrollbarX = getX() + width - SCROLLBAR_WIDTH - 2;
        int scrollbarY = getY() + 2;
        int scrollbarHeight = height - 4;

        context.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, 0xFF1A1A1A);

        int maxScroll = wrappedLines.size() - maxVisibleLines;
        float scrollPercentage = maxScroll > 0 ? (float) scrollOffset / maxScroll : 0;

        int thumbHeight = Math.max(20, (int) ((float) maxVisibleLines / wrappedLines.size() * scrollbarHeight));
        int thumbY = scrollbarY + (int) (scrollPercentage * (scrollbarHeight - thumbHeight));

        boolean isHovering = mouseX >= scrollbarX && mouseX <= scrollbarX + SCROLLBAR_WIDTH
                && mouseY >= thumbY && mouseY <= thumbY + thumbHeight;
        int thumbColor = isDraggingScrollbar ? 0xFF8B8B8B : (isHovering ? 0xFF6B6B6B : 0xFF4B4B4B);

        context.fill(scrollbarX, thumbY, scrollbarX + SCROLLBAR_WIDTH, thumbY + thumbHeight, thumbColor);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!visible) return false;

        if (button == 0 && wrappedLines.size() > maxVisibleLines) {
            int scrollbarX = getX() + width - SCROLLBAR_WIDTH - 2;
            int scrollbarY = getY() + 2;
            int scrollbarHeight = height - 4;

            if (mouseX >= scrollbarX && mouseX <= scrollbarX + SCROLLBAR_WIDTH
                    && mouseY >= scrollbarY && mouseY <= scrollbarY + scrollbarHeight) {

                int maxScroll = wrappedLines.size() - maxVisibleLines;
                float scrollPercentage = maxScroll > 0 ? (float) scrollOffset / maxScroll : 0;
                int thumbHeight = Math.max(20, (int) ((float) maxVisibleLines / wrappedLines.size() * scrollbarHeight));
                int thumbY = scrollbarY + (int) (scrollPercentage * (scrollbarHeight - thumbHeight));

                if (mouseY >= thumbY && mouseY <= thumbY + thumbHeight) {
                    isDraggingScrollbar = true;
                    scrollbarDragStartY = (int) mouseY;
                    scrollbarDragStartOffset = scrollOffset;
                    return true;
                } else {
                    float clickPercentage = (float) (mouseY - scrollbarY) / scrollbarHeight;
                    scrollOffset = Math.max(0, Math.min(maxScroll, (int) (clickPercentage * maxScroll)));
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && isDraggingScrollbar) {
            isDraggingScrollbar = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDraggingScrollbar) {
            int scrollbarHeight = height - 4;
            int maxScroll = wrappedLines.size() - maxVisibleLines;
            int thumbHeight = Math.max(20, (int) ((float) maxVisibleLines / wrappedLines.size() * scrollbarHeight));
            int usableHeight = scrollbarHeight - thumbHeight;

            int dragDelta = (int) mouseY - scrollbarDragStartY;
            int scrollDelta = usableHeight > 0 ? (int) ((float) dragDelta / usableHeight * maxScroll) : 0;

            scrollOffset = Math.max(0, Math.min(maxScroll, scrollbarDragStartOffset + scrollDelta));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (!editable || !isFocused()) return false;

        if (chr == '\r' || chr == '\n') {
            return keyPressed(GLFW.GLFW_KEY_ENTER, 0, modifiers);
        }

        if (chr == '\t') {
            return keyPressed(GLFW.GLFW_KEY_TAB, 0, modifiers);
        }

        if (Character.isISOControl(chr)) {
            return false;
        }

        if (getTotalLength() >= maxLength) {
            return false;
        }

        if (hasSelection) {
            deleteSelection();
        }

        String currentLine = lines.get(cursorLine);
        String newLine = currentLine.substring(0, cursorColumn) + chr + currentLine.substring(cursorColumn);
        lines.set(cursorLine, newLine);
        cursorColumn++;
        updateWrappedLines();

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!editable || !isFocused()) return false;

        boolean ctrlPressed = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;

        if (ctrlPressed && keyCode == GLFW.GLFW_KEY_A) {
            hasSelection = true;
            selectionStartLine = 0;
            selectionStartColumn = 0;
            selectionEndLine = lines.size() - 1;
            selectionEndColumn = lines.get(lines.size() - 1).length();
            cursorLine = selectionEndLine;
            cursorColumn = selectionEndColumn;
            return true;
        }

        if (ctrlPressed && keyCode == GLFW.GLFW_KEY_C) {
            if (hasSelection) {
                String selectedText = getSelectedText();
                GLFW.glfwSetClipboardString(0, selectedText);
            }
            return true;
        }

        if (ctrlPressed && keyCode == GLFW.GLFW_KEY_V) {
            String clipboard = GLFW.glfwGetClipboardString(0);
            if (clipboard != null && !clipboard.isEmpty()) {
                if (hasSelection) {
                    deleteSelection();
                }

                // Remove carriage returns (CR) antes de processar
                clipboard = clipboard.replace("\r", "");

                String[] pasteLines = clipboard.split("\n", -1);

                if (pasteLines.length == 1) {
                    String currentLine = lines.get(cursorLine);
                    String newLine = currentLine.substring(0, cursorColumn) + pasteLines[0] + currentLine.substring(cursorColumn);
                    lines.set(cursorLine, newLine);
                    cursorColumn += pasteLines[0].length();
                } else {
                    String currentLine = lines.get(cursorLine);
                    String beforeCursor = currentLine.substring(0, cursorColumn);
                    String afterCursor = currentLine.substring(cursorColumn);

                    lines.set(cursorLine, beforeCursor + pasteLines[0]);

                    for (int i = 1; i < pasteLines.length - 1; i++) {
                        lines.add(cursorLine + i, pasteLines[i]);
                    }

                    int lastIndex = pasteLines.length - 1;
                    lines.add(cursorLine + lastIndex, pasteLines[lastIndex] + afterCursor);

                    cursorLine += lastIndex;
                    cursorColumn = pasteLines[lastIndex].length();
                }

                updateWrappedLines();
                adjustScroll();
                return true;
            }
            return false;
        }

        if (hasSelection && !ctrlPressed) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_DELETE) {
                deleteSelection();
                return true;
            }
        }

        switch (keyCode) {
            case GLFW.GLFW_KEY_TAB:
                if (getTotalLength() + 4 >= maxLength) return false;

                if (hasSelection) {
                    deleteSelection();
                }

                String currentLine = lines.get(cursorLine);
                String tab = "    ";
                String newLine = currentLine.substring(0, cursorColumn) + tab + currentLine.substring(cursorColumn);
                lines.set(cursorLine, newLine);
                cursorColumn += 4;
                updateWrappedLines();
                return true;

            case GLFW.GLFW_KEY_ENTER:
            case GLFW.GLFW_KEY_KP_ENTER:
                if (getTotalLength() >= maxLength) return false;

                if (hasSelection) {
                    deleteSelection();
                }

                currentLine = lines.get(cursorLine);
                String beforeCursor = currentLine.substring(0, cursorColumn);
                String afterCursor = currentLine.substring(cursorColumn);

                lines.set(cursorLine, beforeCursor);
                lines.add(cursorLine + 1, afterCursor);
                cursorLine++;
                cursorColumn = 0;
                updateWrappedLines();
                adjustScroll();
                return true;

            case GLFW.GLFW_KEY_BACKSPACE:
                if (hasSelection) {
                    deleteSelection();
                } else if (cursorColumn > 0) {
                    String line = lines.get(cursorLine);
                    lines.set(cursorLine, line.substring(0, cursorColumn - 1) + line.substring(cursorColumn));
                    cursorColumn--;
                    updateWrappedLines();
                } else if (cursorLine > 0) {
                    String currentLineText = lines.remove(cursorLine);
                    cursorLine--;
                    cursorColumn = lines.get(cursorLine).length();
                    lines.set(cursorLine, lines.get(cursorLine) + currentLineText);
                    updateWrappedLines();
                    adjustScroll();
                }
                return true;

            case GLFW.GLFW_KEY_DELETE:
                if (hasSelection) {
                    deleteSelection();
                } else {
                    String line = lines.get(cursorLine);
                    if (cursorColumn < line.length()) {
                        lines.set(cursorLine, line.substring(0, cursorColumn) + line.substring(cursorColumn + 1));
                        updateWrappedLines();
                    } else if (cursorLine < lines.size() - 1) {
                        String nextLine = lines.remove(cursorLine + 1);
                        lines.set(cursorLine, line + nextLine);
                        updateWrappedLines();
                    }
                }
                return true;

            case GLFW.GLFW_KEY_LEFT:
                clearSelection();
                if (cursorColumn > 0) {
                    cursorColumn--;
                } else if (cursorLine > 0) {
                    cursorLine--;
                    cursorColumn = lines.get(cursorLine).length();
                    adjustScroll();
                }
                return true;

            case GLFW.GLFW_KEY_RIGHT:
                clearSelection();
                if (cursorColumn < lines.get(cursorLine).length()) {
                    cursorColumn++;
                } else if (cursorLine < lines.size() - 1) {
                    cursorLine++;
                    cursorColumn = 0;
                    adjustScroll();
                }
                return true;

            case GLFW.GLFW_KEY_UP:
                clearSelection();
                if (cursorLine > 0) {
                    cursorLine--;
                    cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                    adjustScroll();
                }
                return true;

            case GLFW.GLFW_KEY_DOWN:
                clearSelection();
                if (cursorLine < lines.size() - 1) {
                    cursorLine++;
                    cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                    adjustScroll();
                }
                return true;

            case GLFW.GLFW_KEY_HOME:
                clearSelection();
                cursorColumn = 0;
                return true;

            case GLFW.GLFW_KEY_END:
                clearSelection();
                cursorColumn = lines.get(cursorLine).length();
                return true;

            case GLFW.GLFW_KEY_PAGE_UP:
                clearSelection();
                scrollOffset = Math.max(0, scrollOffset - maxVisibleLines);
                int visualLine = getVisualLineForCursor();
                if (visualLine < scrollOffset) {
                    // Move cursor to first visible line
                    if (scrollOffset < wrappedLines.size()) {
                        WrappedLine wl = wrappedLines.get(scrollOffset);
                        cursorLine = wl.logicalLine;
                        cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                    }
                }
                return true;

            case GLFW.GLFW_KEY_PAGE_DOWN:
                clearSelection();
                int maxScroll = Math.max(0, wrappedLines.size() - maxVisibleLines);
                scrollOffset = Math.min(maxScroll, scrollOffset + maxVisibleLines);
                visualLine = getVisualLineForCursor();
                if (visualLine >= scrollOffset + maxVisibleLines) {
                    // Move cursor to last visible line
                    int lastVisible = Math.min(scrollOffset + maxVisibleLines - 1, wrappedLines.size() - 1);
                    if (lastVisible >= 0) {
                        WrappedLine wl = wrappedLines.get(lastVisible);
                        cursorLine = wl.logicalLine;
                        cursorColumn = Math.min(cursorColumn, lines.get(cursorLine).length());
                    }
                }
                return true;
        }

        return false;
    }

    private void adjustScroll() {
        int visualLine = getVisualLineForCursor();

        if (visualLine < scrollOffset) {
            scrollOffset = visualLine;
        } else if (visualLine >= scrollOffset + maxVisibleLines) {
            scrollOffset = visualLine - maxVisibleLines + 1;
        }

        int maxScroll = Math.max(0, wrappedLines.size() - maxVisibleLines);
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        int maxScroll = Math.max(0, wrappedLines.size() - maxVisibleLines);
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset - (int)verticalAmount));

        return true;
    }

    private int getTotalLength() {
        int total = 0;
        for (String line : lines) {
            total += line.length();
        }
        total += lines.size() - 1;
        return total;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(net.minecraft.client.gui.screen.narration.NarrationPart.TITLE, getMessage());
    }
}