# Adventure Mod For Fabric

Um mod de sobrevivência e aventura para Minecraft com sistema de calendário, estações, sede e efeitos de altitude.

## 🎮 Funcionalidades

### 📅 Sistema de Calendário
O mod inclui um sistema completo de calendário com estações do ano:

- **Estrutura do Calendário:**
  - 1 ano = 4 estações = 112 dias
  - 1 estação = 28 dias (4 semanas)
  - 1 semana = 7 dias
  - 1 dia = 24000 ticks (~20 minutos reais)

- **Estações:**
  - 🌸 **Primavera** - Crescimento acelerado de plantas (1.5x)
  - ☀ **Verão** - Dias mais longos, sede aumentada (1.5x)
  - 🍂 **Outono** - Chuvas frequentes
  - ❄ **Inverno** - Dias curtos, mais mobs hostis

### 💧 Sistema de Sede
- Barra de sede separada da fome
- Aumenta mais rápido no verão
- Diminui mais devagar no inverno

### 🏔️ Efeitos de Altitude
- Mal da montanha em altitudes elevadas (Y > 128)
- Sistema de aclimatação
- Efeitos como fraqueza, náusea e lentidão

## 📋 Comandos

| Comando | Descrição |
|---------|-----------|
| `/calendar info` | Mostra informações completas do calendário |
| `/calendar date` | Mostra a data atual |
| `/calendar time` | Mostra a hora atual |
| `/calendar season` | Mostra a estação atual |
| `/date` | Atalho para `/calendar date` |
| `/season` | Atalho para `/calendar season` |

### Comandos de Admin (requer permissão nível 2)
| Comando | Descrição |
|---------|-----------|
| `/calendar set day <1-112>` | Define o dia do ano |
| `/calendar set year <N>` | Define o ano |
| `/calendar set season <spring\|summer\|autumn\|winter>` | Define a estação |

## ⚙️ Configuração

O arquivo de configuração `adventuremod.json` é criado automaticamente na pasta `config/`:

```json
{
  "debug": false,
  "altitudeEffect": true,
  "thirst": true,
  "calendarEnabled": true,
  "calendarHudEnabled": true,
  "daysPerSeason": 28
}
```

| Opção | Descrição | Padrão |
|-------|-----------|--------|
| `debug` | Ativa logs de debug | `false` |
| `altitudeEffect` | Ativa efeitos de altitude | `true` |
| `thirst` | Ativa sistema de sede | `true` |
| `calendarEnabled` | Ativa sistema de calendário | `true` |
| `calendarHudEnabled` | Mostra HUD do calendário | `true` |
| `daysPerSeason` | Dias por estação | `28` |

## 🌡️ Modificadores por Estação

| Estação | Temperatura | Crescimento | Chuva | Sede | Mobs |
|---------|-------------|-------------|-------|------|------|
| Primavera | 0.0 | 1.5x | 40% | 1.0x | 1.0x |
| Verão | +0.5 | 1.2x | 15% | 1.5x | 0.8x |
| Outono | -0.2 | 0.8x | 50% | 0.9x | 1.1x |
| Inverno | -0.8 | 0.3x | 60% | 0.7x | 1.3x |

## 📦 Dependências

- **Minecraft:** 1.21.1
- **Fabric Loader:** >=0.15.0
- **Fabric API:** *

### Dependência Opcional
- [Adventure Seasons](https://github.com/cortezvinicius1997-gif/adventure_seasons) - Para compatibilidade com sistema de estações externo

## 🔧 Instalação

1. Instale o [Fabric Loader](https://fabricmc.net/use/)
2. Baixe e instale o [Fabric API](https://modrinth.com/mod/fabric-api)
3. Coloque o arquivo `.jar` do Adventure Mod na pasta `mods/`

## 📜 Licença

All Rights Reserved

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
