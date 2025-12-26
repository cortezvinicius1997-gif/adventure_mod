local pos = player:getPos()

local x = pos:getX()
local y = pos:getY() + 500
local z = pos:getZ()

player:teleport(x, y, z)